package com.tuibei.service.user;

import com.tuibei.model.user.User;
import com.tuibei.utils.ResultObject;

public interface UserService {
    ResultObject toRegistry(User user) throws Exception;

    ResultObject toLogin(User user)throws Exception;
}
