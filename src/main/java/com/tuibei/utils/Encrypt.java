package com.tuibei.utils;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
/**
 * 解密json
 */
public class Encrypt {

    private String url;
    private String member_id;
    private  String currentTime;
}
