package com.tuibei.service.about;

import com.tuibei.model.about.FeedBack;
import com.tuibei.utils.ResultObject;

public interface FeedBackService {
    ResultObject saveFeedBack(FeedBack feedback);
}
