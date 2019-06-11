package com.tuibei.model.kdn;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TraceInfo {
    private String traceNum;
    private String shipperName;//快递公司名
    private String code;//快递公司编码
    private String member_id;
}
