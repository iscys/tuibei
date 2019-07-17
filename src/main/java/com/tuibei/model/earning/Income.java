package com.tuibei.model.earning;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Income {

    private String member_id;
    private double price;
    private int  type;
    private String order_sn;


}
