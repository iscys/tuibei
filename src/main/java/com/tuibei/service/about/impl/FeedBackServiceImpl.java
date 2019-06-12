package com.tuibei.service.about.impl;

import com.tuibei.mapper.about.FeedBackMapper;
import com.tuibei.model.about.FeedBack;
import com.tuibei.service.about.FeedBackService;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeedBackServiceImpl implements FeedBackService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private FeedBackMapper feedBackMapper;

    @Override
    public ResultObject saveFeedBack(FeedBack feedback) {
        feedBackMapper.saveFeedBack(feedback);
        return ResultObject.success(null);

    }
}
