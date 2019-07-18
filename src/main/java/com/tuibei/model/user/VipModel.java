package com.tuibei.model.user;

import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class VipModel {
    private String id;
    private String member_id;//用户id
    private String vip_expire_time;//失效时间YYYY/mm/dd HH:mi
    private long vip_expire_time_long;
    private String level_id;//类型新用户以及使用了免费次数的用户为0 月卡1 年卡2
    private String update_time;
    private int use_free;//是否使用了免费的天数
    private double account;//余额
    private int expire;//是否过期 1已经过期 0未过期
    private int expireDay;//是否过期 1已经过期 0未过期


}
