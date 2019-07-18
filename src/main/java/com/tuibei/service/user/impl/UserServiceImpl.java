package com.tuibei.service.user.impl;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.tuibei.mapper.sms.AliSmsMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.sms.PhoneCode;
import com.tuibei.model.user.User;
import com.tuibei.model.user.VipModel;
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

import java.time.LocalDate;
import java.time.Period;

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
        user.setNickname(Constant.COMMON.DEFAAULTNICKNAME);//使用默认昵称
        user.setHeadimgurl(Constant.COMMON.DEFAAUL_HEADIMGURL);//使用默认头像
        String timeInSecond = DateUtils.getTimeInSecond();
        user.setVip_expire_time(timeInSecond);
        user.setLast_login(DateUtils.getTimeInSecond());
        userMapper.saveNewUser(user);//保存新用户
        userMapper.saveInitVipInfo(user);//保存新用户vip 初始化信息
        logger.info("用户member_id：{} 注册成功",user.getMember_id());
        user.setPassword("************");
        user.setVip_expire_time(LocalDate.now().toString());
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
        logger.info("用户手机：{} 开始进行登录",user.getPhone());
        user.setPassword(ToolsUtils.getMD5String(user.getPassword()));
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Constant.VALIDATE_MEMBER_ERROR_CODE,Constant.VALIDATE_MEMBER_ERROR_CODE_MESSAGE,null);
        }
        logger.info("得到账号：{} 信息：{}",user.getPhone(),userInfo.toString());
        userInfo.setPassword("***********");
        logger.info("获取账号：{} vip信息",user.getPhone());
        VipModel vip=userMapper.getVipInfo(userInfo);
        userInfo.setLevel_id(vip.getLevel_id());
        userInfo.setVip_expire_time(vip.getVip_expire_time());
        userInfo.setAccount(vip.getAccount());
        userInfo.setUse_free(vip.getUse_free());
        logger.info("得到账号：{} vip信息：{}",user.getPhone(),vip.toString());
        user.setLast_login(DateUtils.getTimeInSecond());
        userMapper.modifyUser(user);
        return ResultObject.success(userInfo);
    }

    @Override
    public ResultObject toModify(User user) throws Exception {
        User checkUser =new User();
        checkUser.setPhone(user.getPhone());
        User isExist=userMapper.getUserInfo(checkUser);
        if(null==isExist){
            logger.error("用户手机 ：{} 尚未被注册" ,user.getPhone());
            return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);
        }
        checkUser=null;
        isExist=null;
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
        user.setPassword(ToolsUtils.getMD5String(user.getPassword()));
        userMapper.modifyUser(user);

        return ResultObject.success(null);
    }

    @Override
    public ResultObject toModifySelf(User user) throws Exception {

        userMapper.modifyUser(user);
        return ResultObject.success(null);
    }

    @Override
    public ResultObject getUserInfo(User user) {

        User result=userMapper.getUserInfoAndVipInfo(user);

        return ResultObject.success(result);
    }

    
    @Override
    public ResultObject getExpireTime(User user)throws Exception {

        VipModel vipInfo = userMapper.getVipInfo(user);
        long timeInSecond = DateUtils.getTimeInSecond_long();
        long vip_expire_time_long = vipInfo.getVip_expire_time_long();
        if(timeInSecond>vip_expire_time_long){
            vipInfo.setExpire(1);
        }else{
            vipInfo.setExpire(0);
            long diff = vip_expire_time_long - timeInSecond;
            int day =(int) (diff / (24 * 60 * 60));
            vipInfo.setExpireDay(day); //失效时间

        }
        
        return ResultObject.success(vipInfo);
    }


    public static void main(String[] args) {
        long start=1563130000;
        Long end = DateUtils.getTimeInSecond_long();
        System.out.println((start-end)/(24*60*60));
    }

}
