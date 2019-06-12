package com.tuibei.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class ExpressRecord implements Serializable {


    private String member_id;
    private String trace_num;
    private String operation_time;
    private Integer operation_type;
    private String remark;
    private String ship_code;
    private String operator;
    private String img;
}
