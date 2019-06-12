package com.tuibei.service.kuaidi.impl;

import com.tuibei.mapper.kuaidi.MarkExpressMapper;
import com.tuibei.mapper.user.UserMapper;
import com.tuibei.model.ExpressRecord;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.user.User;
import com.tuibei.service.kuaidi.MarkExpressService;
import com.tuibei.utils.DateUtils;
import com.tuibei.utils.Page;
import com.tuibei.utils.PageData;
import com.tuibei.utils.ResultObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class MarkExpressServiceImpl implements MarkExpressService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private MarkExpressMapper markExpressMapper;
    @Autowired
    private UserMapper userMapper;

    /**
     * 记录列表
     * @param pd
     * @return
     */
    @Override
    public ResultObject getRecordList(PageData pd) {
        logger.info("获取记录列表");
        String pageNum = pd.get("pageNum").toString();
        Integer totalRecord =markExpressMapper.getRecordListCount(pd);
        Page page =new Page(Integer.valueOf(pageNum),totalRecord,Constant.COMMON.PAGESIZE);
        pd.put("startIndex",page.getStartIndex());
        pd.put("pageSize",Constant.COMMON.PAGESIZE);


        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",Integer.valueOf(pageNum));//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        List<ExpressRecord > expressList = markExpressMapper.getRecordList(pd);
        if(pageNum.equals("1")) {
            pd.put("operation_type","2");//异常type
            pd.put("month", DateUtils.stableMonth());
            Integer monthRecord=markExpressMapper.getMonthRecord(pd);
            pd.put("day", DateUtils.stableDay());
            Integer dayRecord= markExpressMapper.getDayRecord(pd);
            Integer exception=markExpressMapper.getRecordListCount(pd);
            res.put("monthRecord", monthRecord);//月记录
            res.put("dayRecord", dayRecord);//日记录
            res.put("excRecord", exception);//异常记录
        }
        res.put("List",expressList);

        return ResultObject.success(res);
    }

    @Override
    public ResultObject tagExpressType(PageData pd) {
        logger.info("快递打标记");
        String trace_num = pd.get("trace_num").toString();
        String member_id = pd.get("member_id").toString();
        User user =new User();
        user.setMember_id(member_id);
        User userInfo = userMapper.getUserInfo(user);
        if(null==userInfo){
            return ResultObject.build(Constant.MEMBER_XXX_NULL,Constant.MEMBER_XXX_NULL_MESSAGE,null);
        }
        ExpressRecord exp =new ExpressRecord();
        exp.setMember_id(member_id);
        exp.setTrace_num(trace_num);
        ExpressRecord expRecord=markExpressMapper.getExpressInfo(exp);
        if(null !=expRecord) {
            logger.info("已有快递单号打标记");
            markExpressMapper.tagExpressType(pd);
        }else{
            logger.info("添加新的快递标记信息");
            markExpressMapper.addTagExpressType(pd);
        }
        return ResultObject.success(null);
    }

}
