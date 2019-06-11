package com.tuibei.service.kuaidi.impl;

import com.tuibei.model.constant.Constant;
import com.tuibei.model.user.User;
import com.tuibei.service.kuaidi.MarkExpressService;
import com.tuibei.utils.Page;
import com.tuibei.utils.ResultObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;

@Service
@Transactional
public class MarkExpressServiceImpl implements MarkExpressService {


    @Override
    public ResultObject getRecordList(User user, HashMap<String,String> map) {

        String pageNum = map.get("pageNum");
        Page page =new Page(Integer.valueOf(pageNum),100,Integer.valueOf(Constant.COMMON.PAGESIZE));
        map.put("startIndex",String.valueOf(page.getStartIndex()));
        map.put("pageSize",Constant.COMMON.PAGESIZE);


        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",pageNum);//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数

        if(pageNum.equals("1")) {
            res.put("monthRecord", page.getTotalPage());//月记录
            res.put("dayRecord", page.getTotalPage());//日记录
            res.put("excRecord", page.getTotalPage());//异常记录
        }

        return ResultObject.success(res);
    }
}
