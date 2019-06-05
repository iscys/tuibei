package com.tuibei.model;

import java.io.Serializable;

public class KuaidiCommonTemplate implements Serializable {


    private String accept_location;
    private String accept_time;

    public String getAccept_location() {
        return accept_location;
    }

    public void setAccept_location(String accept_location) {
        this.accept_location = accept_location;
    }

    public String getAccept_time() {
        return accept_time;
    }

    public void setAccept_time(String accept_time) {
        this.accept_time = accept_time;
    }
}
