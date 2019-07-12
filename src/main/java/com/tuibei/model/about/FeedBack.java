package com.tuibei.model.about;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@ToString
public class FeedBack implements Serializable {
    private String member_id;
    private String name;
    private String phone;
    private String feedback;
}
