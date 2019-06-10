package com.tuibei.service.user.impl;

import cn.binarywang.wx.miniapp.api.impl.WxMaServiceImpl;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.Constant;
import com.tuibei.model.User;
import com.tuibei.service.user.UserService;
import com.tuibei.utils.ResultObject;
import com.tuibei.utils.ToolsUtils;
import me.chanjar.weixin.common.error.WxErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private WxMaServiceImpl wxsmall;//微信小程序服务
    @Autowired
    private UserMapper userMapper;
    @Override
    public ResultObject toRegistry(User user) throws Exception {
        String invite_code = user.getInvite_code();
        if(!StringUtils.isEmpty(invite_code)){
            logger.info("检测推广人信息 invite_code：{}" ,invite_code);
            User invit =new User();
            invit.setInvite_code(invite_code);
            User master=userMapper.getUserInfo(invit);
            if(master==null){
                //推广码无效让用户重新注册
                logger.error("无效推广码 invite_code：{}" ,invite_code);
                return ResultObject.build(Constant.INVITE_CODE_ERROR,Constant.INVITE_CODE_ERROR_MESSAGE,null);
            }else{
                //设置用户的推广人
                user.setMaster(master.getMember_id());
            }

        }
        //微信小程序获取openid unionid sessionkey
        WxMaJscode2SessionResult wxsmallInfo = null;
        try {
            wxsmallInfo = wxsmall.jsCode2SessionInfo(user.getCode());
        } catch (WxErrorException e) {
            return ResultObject.build(Constant.WX_ERROR, e.getMessage(), e.getMessage());
        }
        String openid = wxsmallInfo.getOpenid();
        String unionid = wxsmallInfo.getUnionid();
        user.setOpenid(openid);
        user.setUnionid(StringUtils.isEmpty(unionid)?"":unionid);

        //密码加密
        user.setPassword(ToolsUtils.getMD5String(user.getPassword()));
        return null;
    }
}