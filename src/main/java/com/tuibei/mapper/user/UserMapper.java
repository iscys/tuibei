package com.tuibei.mapper.user;

import com.tuibei.model.user.User;
import com.tuibei.model.user.VipModel;

public interface UserMapper {
    User getUserInfo(User invit);

    void saveNewUser(User user);

    void saveInitVipInfo(User user);

    void modifyUser(User user);

    VipModel getVipInfo(User userInfo);

    void updateVipInfo(User user);

    User getUserInfoAndVipInfo(User user);
}
