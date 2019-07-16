package com.tuibei.model.rule;

import lombok.Data;

@Data
public class Rule {

    private int min_count;
    private int max_count;
    private String discount;
    private String price;
    private String rule_start_time;
    private String rule_end_time;
    private String create_time;


}
