package com.tuibei.mapper.user;

import com.tuibei.model.user.User;

public interface UserMapper {
    User getUserInfo(User invit);

    void saveNewUser(User user);
}
