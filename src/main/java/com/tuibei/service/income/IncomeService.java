package com.tuibei.service.income;

import com.tuibei.utils.PageData;
import com.tuibei.utils.ResultObject;

public interface IncomeService {
    ResultObject getIncomeList(PageData pd) throws Exception;
}
