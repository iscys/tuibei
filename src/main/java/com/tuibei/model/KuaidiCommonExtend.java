package com.tuibei.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class KuaidiCommonExtend extends KuaidiCommonTemplateDetail {

    private Integer operation_type=0;//操作类型

    private String member_id;//操作人ID

    private String remark;

}
