package com.tuibei.model.kdn;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class TraceInfo {
    private String traceNum;
    private String shipperName;//快递公司名
    private String ship_code;//快递公司编码
    private String operator;//快递公司
    private String member_id;
    @JsonIgnore
    private String version;
}
