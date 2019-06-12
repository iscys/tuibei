package com.tuibei.service.user.impl;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.tuibei.mapper.sms.AliSmsMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.PhoneCode;
import com.tuibei.model.user.User;
import com.tuibei.service.user.UserService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMaServiceImpl wxsmall;//微信小程序服务
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AliSmsMapper smsMapper;

    /**
     *  regist  step:
     *  1.检测手机是否被注册过
     *  2.如果有邀请人码那么就查看邀请人是否存在
     *  3.检测手机验证码是否正确
     *  4.通过wx code 获取用户openid ,unionid(开放平台)
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject toRegistry(User user) throws Exception {
        String invite_code = user.getInvite_code();

        //step 1:
        logger.info("检测用户：{}是否已经被注册" ,user.getPhone());
        User checkUser =new User();
        checkUser.setPhone(user.getPhone());
        User isExist=userMapper.getUserInfo(checkUser);
        if(null!=isExist){
            logger.error("用户手机 ：{} 已经被注册" ,user.getPhone());
            return ResultObject.build(Constant.PHONE_EXIST,Constant.PHONE_EXIST_MESSAGE,null);
        }
        checkUser=null;//help gc
        //step 2:
        if(!StringUtils.isEmpty(invite_code)){
            logger.info("检测推广人信息 invite_code：{}" ,invite_code);
            User invit =new User();
            invit.setInvite_code(invite_code);
            User master=userMapper.getUserInfo(invit);
            invit=null;//help gc
            if(null==master){
                //推广码无效让用户重新注册
                logger.error("无效推广码 invite_code：{}" ,invite_code);
                return ResultObject.build(Constant.INVITE_CODE_ERROR,Constant.INVITE_CODE_ERROR_MESSAGE,null);
            }else{
                //设置用户的推广人
                user.setMaster(master.getMember_id());
            }
        }

        //step 3: 验证手机验证码
        Integer phone_code = Integer.valueOf(user.getPhone_code());
        PhoneCode phone =new PhoneCode();
        phone.setPhone(user.getPhone());
        PhoneCode phoneCheck = smsMapper.getRecentPhoneCode(phone);
        phone=null;
        if(null==phoneCheck ||DateUtils.getTimeInSecond_long()>phoneCheck.getExpire_time()|| !phone_code.equals(phoneCheck.getCode())){
            logger.warn("无效手机验证码：{}",user.getPhone());
            return ResultObject.build(Constant.EXPIRE_PHONE_CODE,Constant.EXPIRE_PHONE_CODE_MESSAGE,null);
        }


        //step 4:
        //微信小程序获取openid unionid sessionkey
        WxMaJscode2SessionResult wxsmallInfo = null;
        try {
            wxsmallInfo = wxsmall.jsCode2SessionInfo(user.getCode());
        } catch (WxErrorException e) {
            return ResultObject.build(Constant.WX_ERROR, e.getMessage(), e.getMessage());
        }
         String openid = wxsmallInfo.getOpenid();
         String unionid = wxsmallInfo.getUnionid();
         User openidExist =new User();
         openidExist.setOpenid(openid);
         User isOpenidExist=userMapper.getUserInfo(openidExist);
         openidExist =null;
        if(null!=isOpenidExist){
            logger.error("用户openid ：{} 已经被注册" ,openid);
            return ResultObject.build(Constant.PHONE_EXIST,Constant.PHONE_EXIST_MESSAGE,null);
        }
        user.setOpenid(openid);
        user.setUnionid(StringUtils.isEmpty(unionid)?"":unionid);

        //密码加密
        user.setPassword(ToolsUtils.getMD5String(user.getPassword()));
        //生成邀请码8位
        String code_invite=ToolsUtils.generateShortUuid();
        user.setInvite_code(code_invite);
        //用户member_id
        user.setMember_id(ToolsUtils.idGenerate());
        user.setTime(DateUtils.getTimeInSecond());
        user.setVip_expire_time(DateUtils.getTimeInSecond());
        userMapper.saveNewUser(user);//保存新用户
        userMapper.saveInitVipInfo(user);//保存新用户vip 初始化信息
        logger.info("用户member_id：{} 注册成功",user.getMember_id());
        user.setPassword("************");
        return ResultObject.success(user);
    }

    /**
     * 用户登录
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject toLogin(User user) throws Exception {
        
        user.setPassword(ToolsUtils.getMD5String(user.getPassword()));
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Constant.VALIDATE_MEMBER_ERROR_CODE,Constant.VALIDATE_MEMBER_ERROR_CODE_MESSAGE,null);
        }
        userInfo.setPassword("***********");
        return ResultObject.success(userInfo);
    }

    public static void main(String[] args) {
        System.out.println(ToolsUtils.getMD5String("123456"));
    }
}
