package com.tuibei.controller.kuaidi;

import com.tuibei.controller.BaseController;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.user.User;
import com.tuibei.service.kuaidi.MarkExpressService;
import com.tuibei.utils.Page;
import com.tuibei.utils.PageData;
import com.tuibei.utils.ResultObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping("/mark")
public class MarkExpressController extends BaseController {


    @Autowired
    private MarkExpressService markService;

    @PostMapping("/list")
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

    /**
     * 标记快递状态
     * @return
     */
    @PostMapping("/tag")
    public ResultObject tag(){
        PageData pd = this.getPageData();
        Object member_id = pd.get("member_id");
        Object operation_type = pd.get("operation_type");
        Object trace_num = pd.get("trace_num");
        if(StringUtils.isEmpty(member_id)){
            return ResultObject.build(Constant.MEMBER_NULL,Constant.MEMBER_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(operation_type)){
            return ResultObject.build(Constant.OPERATION_TYPE_NULL,Constant.OPERATION_TYPE_NULL_MESSAGE,null);
        }
        if(StringUtils.isEmpty(trace_num)){
            return ResultObject.build(Constant.TRACE_NUM_NULL,Constant.TRACE_NUM_NULL_MESSAGE,null);
        }
        ResultObject result = markService.tagExpressType(pd);
        return result;
    }


}
