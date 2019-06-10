package com.tuibei.utils;


import java.io.Serializable;

/**
 * 返回前端的数据结构
 * @author iscys
 */
public class ResultObject implements Serializable {

    //**返回成功失败标志**//
    private String code;
    //**返回的成功错误信息**//
    private String error;
    //**返回的数据**//
    private Object data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    /**
     * 自定义构造返回数据结构类型
     * @param flag
     * @param error
     * @param data
     * @return
     */
    public static ResultObject build (String flag,String error,Object data){

        return  new ResultObject(flag,error,data);
    }



    ResultObject(String code, String error , Object data){
        this.code=code;
        this.error=error;
        this.data=data;
    }

    public static ResultObject success (Object data){

        return  new ResultObject("200","success",data);
    }

    public static ResultObject error (Object data){

        return  new ResultObject("500","error",data);
    }


}
