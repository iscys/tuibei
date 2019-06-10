package com.tuibei.model.sms;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
@Data
@ToString
public class SmsContent implements Serializable {

    private String phone;
    private String templateCode;
    private String templateParam;
    private String signName;

}
