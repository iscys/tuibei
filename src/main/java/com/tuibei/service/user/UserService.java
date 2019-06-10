package com.tuibei.service.user;

import com.tuibei.model.User;
import com.tuibei.utils.ResultObject;

public interface UserService {
    ResultObject toRegistry(User user) throws Exception;
}
