package com.tuibei.controller.kuaidi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

 private Logger logger =LoggerFactory.getLogger(MainController.class);

    @RequestMapping("/ind")
    @ResponseBody
    public String ind(){
        logger.info("11111");
        return "11111";
    }
}
