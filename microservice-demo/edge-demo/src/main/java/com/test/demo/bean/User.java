package com.test.demo.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {

    private String userId;
    private String name;
    private String gender;
    private String remark;

}
