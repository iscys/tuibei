package com.tuibei.mapper.kuaidi;

import com.tuibei.model.ExpressRecord;

import java.util.HashMap;
import java.util.List;

public interface MarkExpressMapper {
    List<ExpressRecord> getRecordList(HashMap<String, String> map);

    Integer getRecordListCount(HashMap<String, String> map);


    Integer getDayRecord(HashMap<String, String> map);

    Integer getMonthRecord(HashMap<String, String> map);
}
