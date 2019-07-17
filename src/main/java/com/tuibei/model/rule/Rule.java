package com.tuibei.model.rule;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Rule {

    private String first_discount;
    private String second_discount;
    private String third_discount;
    private String rule_end_time;
    private int  total_times;
    private String flag;



}
