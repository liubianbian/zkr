package com.test.gateway.constant;

/**
 * 常量枚举变量
 */
public enum ConstantEnum {
    
    /**计算接口耗时静态变量**/
    SYSTEM_START_TIME("systemStartTime"),
    
    /**日志打印变量标识**/
    RECORDER_PRINT_LOG("recorderPrintLog"),
    
    /**POST请求数据内容体标识**/
    REQUEST_BODY_CONTENT("requestBodyContent"),
    
    /**响应数据内容体标识**/
    RESPONSE_BODY_CONTENT("responseBodyContent"),
    
    /**重定向URL标识**/
    REDIRECT_URL("redirectUrl"),
    
    /**会员中心前后端交互统一的请求参数名称**/
    REQUEST_PARAM_NAME("param"),
    
    /**应用层接口统一返回值特征标识**/
    RESP_CODE("respCode"),
    RESP_DESC("respDesc"),
    
    /**日志数据采集-日志归属类型**/
    LOG_TYPE("logType"),
    
    /**日志数据采集-日志归属类型**/
    LOG_TYPE_REQUEST("request"),
    
    /**日志数据采集-日志归属类型**/
    LOG_TYPE_PROXY("proxy"),
    
    /**日志数据采集-日志归属类型**/
    LOG_TYPE_RESPONSE("response"),
    
    /**日志数据采集-日志请求唯一标识符**/
    LOG_TRACE_ID("traceId"),
    
    /**日志数据采集-请求路径**/
    LOG_REQ_PATH("reqPath"),
    
    /**日志数据采集-请求方式**/
    LOG_REQ_METHOD("reqMethod"),
    
    /**日志数据采集-请求方式**/
    LOG_CONTENT_TYPE("contentType"),
    
    /**日志数据采集-请求IP**/
    LOG_REQ_IP("reqIp"),
    
    /**日志数据采集-用户代理**/
    LOG_REQ_USER_AGENT("userAgent"),
    
    /**日志数据采集-请求时间**/
    LOG_REQ_DATE("reqDate"),
    
    /**日志数据采集-请求数据**/
    LOG_REQ_DATA("reqData"),
    
    /**日志数据采集-返回时间**/
    LOG_RES_DATE("resDate"),
    
    /**日志数据采集-返回数据**/
    LOG_RES_DATA("resData"),
    
    /**日志数据采集-请求响应耗时**/
    LOG_EXPEND_TIME("expendTime"),
    
    /**日志数据采集-用户ID**/
    LOG_REQ_USERID("userId"),
    
    /**日志数据采集-用户JWT**/
    LOG_REQ_JWT("jwt"),
    
    /**日志数据采集-提示语**/
    LOG_TRACE_MSG("traceMsg"),
    
    /**日志数据采集-应用服务请求路径**/
    LOG_SERVICE_PATH("servicePath"),
    
    /**智能客服APP端H5传输会员中心用户Cookie名称**/
    MEMBER_ID("member_id"),
    
    /**Request请求Header Authorization**/
    REQUEST_HEADER_AUTHORIZATION("Authorization"),
    
    /**Response响应Header 自定义Token**/
    RESPONSE_HEADER_TOKEN("token"),
    
    /**Request请求Header User-Agent**/
    USER_AGENT("User-Agent"),
    
    /**第三方系统接口请求参数名称**/
    SYSPARAM("sysparam"),
    BUSIPARAM("busiparam"),
    
    /**接口限频次数**/
    ACCESS_LIMIT_TIMES("50"),
    
    /**5分钟剩余有效期,单位为毫秒**/
    FIVE_MINUTE("300000"),
    
    /**登录有效时间，单位为秒**/
    LOGIN_EXPIRE("1800"),
    
    /**登录接口uri**/
    APP_URL_FIELD("url"),
    
    ;
    
    private String name;

    private ConstantEnum(String name) {
        this.name = name;
    }

    public String value() {
        return this.name;
    }
    
    public int toInt(){
    	return Integer.valueOf(this.name);
    }
}
