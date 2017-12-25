package com.example.demo.DO;

import java.io.Serializable;

/**
 * Created by zonzie on 2017/12/12.
 */
public class City implements Serializable {
    private String id;
    private String name;
    private String province;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
