package com.tuibei.service.kuaidi;

import com.tuibei.model.user.User;
import com.tuibei.utils.Page;
import com.tuibei.utils.ResultObject;

import java.util.HashMap;

public interface MarkExpressService {
    ResultObject getRecordList(User user, HashMap<String,String> map);
}
