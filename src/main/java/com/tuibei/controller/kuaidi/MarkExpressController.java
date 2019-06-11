package com.tuibei.controller.kuaidi;

import com.tuibei.controller.BaseController;
import com.tuibei.model.user.User;
import com.tuibei.service.kuaidi.MarkExpressService;
import com.tuibei.utils.Page;
import com.tuibei.utils.PageData;
import com.tuibei.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mark")
public class MarkExpressController extends BaseController {


    @Autowired
    private MarkExpressService markService;

    @RequestMapping("/list")
    public ResultObject recordList(){
        PageData pd = this.getPageData();
        String pageNum = pd.getString("pageNum");
        if(StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }
        ResultObject result = markService.getRecordList(pd);

        return result;
    }

}
