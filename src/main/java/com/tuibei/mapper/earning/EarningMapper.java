package com.tuibei.mapper.earning;

import com.tuibei.model.earning.Earning;
import com.tuibei.model.earning.Income;

public interface EarningMapper {
    void saveEarningLog(Earning ng);

    void saveIncomeInfo(Income income);
}
