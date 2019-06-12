package com.tuibei.controller.about;

import com.tuibei.controller.BaseController;
import com.tuibei.model.about.FeedBack;
import com.tuibei.utils.ResultObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("about")
public class FeedBackController extends BaseController {


    @PostMapping("/feedback")
    public ResultObject feedBack(FeedBack feedback){
        return null;
    }
}
