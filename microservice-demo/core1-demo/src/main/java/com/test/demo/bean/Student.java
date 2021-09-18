package com.test.demo.bean;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: luofang
 * @create: 2019-08-26 19:33
 **/
@TableName("STUDENT")
@Data
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private int id;

    @TableField("NAME")
    private String name;

    @TableField("SEX")
    private String sex;



}
