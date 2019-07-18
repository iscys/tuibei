package com.tuibei.service.income.impl;

import com.tuibei.mapper.earning.EarningMapper;
import com.tuibei.model.ExpressRecord;
import com.tuibei.model.constant.Constant;
import com.tuibei.model.earning.Income;
import com.tuibei.service.income.IncomeService;
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
public class IncomeServiceImpl implements IncomeService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EarningMapper incomeMapper;
    /**
     * 获取收益
     * @param pd
     * @return
     * @throws Exception
     */
    @Override
    public ResultObject getIncomeList(PageData pd) throws Exception {

        logger.info("获取收入记录列表");
        String pageNum = pd.get("pageNum").toString();
        Integer totalRecord =incomeMapper.getIncomeListCount(pd);
        Page page =new Page(Integer.valueOf(pageNum),totalRecord, Constant.COMMON.PAGESIZE);
        pd.put("startIndex",page.getStartIndex());
        pd.put("pageSize",Constant.COMMON.PAGESIZE);


        HashMap<String,Object> res =new HashMap<String,Object>();
        res.put("pageNum",Integer.valueOf(pageNum));//c传过来到页数
        res.put("totalPage",page.getTotalPage());//总页数
        List<Income> incomeList = incomeMapper.getIncomeList(pd);
        res.put("incomeList",incomeList);

        return ResultObject.success(res);
    }
}
