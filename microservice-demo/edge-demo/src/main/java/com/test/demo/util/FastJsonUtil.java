package com.test.demo.util;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 通用JSON工具类
 */
public class FastJsonUtil {
    
    public static String toJson(Object object) {
        return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
    }

    
}
