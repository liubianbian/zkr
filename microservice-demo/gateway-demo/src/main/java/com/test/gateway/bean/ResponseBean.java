package com.test.gateway.bean;

import lombok.Data;

/**
 * 通用接口返回数据映射对象
 */
@Data
public class ResponseBean<T>{

    /**响应编码**/
    private String respCode;
    
    /**响应描述**/
    private String respDesc;
    
    /**响应数据**/
    private T respData;

}
