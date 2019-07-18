package com.tuibei.mapper.earning;

import com.tuibei.model.earning.Earning;
import com.tuibei.model.earning.Income;
import com.tuibei.utils.PageData;

import java.util.List;

public interface EarningMapper {
    void saveEarningLog(Earning ng);

    void saveIncomeInfo(Income income);

    Integer getIncomeListCount(PageData pd);

    List<Income> getIncomeList(PageData pd);
}
