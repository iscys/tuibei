package com.tuibei.service.user;

import com.tuibei.model.user.User;
import com.tuibei.utils.ResultObject;

public interface UserService {
    ResultObject toRegistry(User user) throws Exception;

    ResultObject toLogin(User user)throws Exception;

    ResultObject toModify(User user) throws Exception;

    ResultObject toModifySelf(User user) throws Exception;

    ResultObject getUserInfo(User user);

    ResultObject getExpireTime(User user) throws Exception;

    ResultObject getInviteInfo(User user) throws Exception;
}
