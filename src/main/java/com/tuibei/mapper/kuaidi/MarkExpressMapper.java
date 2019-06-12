package com.tuibei.mapper.kuaidi;

import com.tuibei.model.ExpressRecord;
import com.tuibei.utils.PageData;

import java.util.HashMap;
import java.util.List;

public interface MarkExpressMapper {
    List<ExpressRecord> getRecordList(HashMap<String, String> map);

    Integer getRecordListCount(HashMap<String, String> map);


    Integer getDayRecord(HashMap<String, String> map);

    Integer getMonthRecord(HashMap<String, String> map);

    void tagExpressType(PageData pd);

    ExpressRecord getExpressInfo(ExpressRecord exp);

    void addTagExpressType(PageData pd);
}
