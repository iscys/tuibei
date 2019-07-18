package com.tuibei.controller.income;

import com.tuibei.controller.BaseController;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.earning.Income;
import com.tuibei.model.user.User;
import com.tuibei.service.income.IncomeService;
import com.tuibei.utils.PageData;
import com.tuibei.utils.ResultObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/income")
public class IncomeController extends BaseController {

    @Autowired
    private IncomeService incomeService;

    @RequestMapping("/list")
    public ResultObject incomeList(){
        PageData pd = this.getPageData();
        String member_id = pd.getString("member_id");
        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Constant.MEMBER_NULL,Constant.MEMBER_NULL_MESSAGE,null);
        }

        String pageNum = pd.getString("pageNum");
        if(org.springframework.util.StringUtils.isEmpty(pageNum)||pageNum.equals("0")){
            pageNum ="1";
            pd.put("pageNum",pageNum);
        }
        try {
            return incomeService.getIncomeList(pd);
        }catch (Exception e){
            return ResultObject.error(null);
        }

    }


}
