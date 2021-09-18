//package com.cignacmb.demo.util;
//
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Enumeration;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import javax.servlet.http.HttpServletRequest;
//
//import lombok.extern.slf4j.Slf4j;
//
//import com.cignacmb.base.constant.ParamEnum;
//import com.cignacmb.base.util.CommonUtil;
//
///**
// * WEb相关工具类
// * @author j1mei
// * 2017年1月5日 下午4:21:38
// */
//@Slf4j
//public class WebUtil {
//
//    /**
//     * 手机浏览器USER-AGENT标识正则表达式
//     */
//    private static String phoneReg = "\\b(ip(hone|od)|android|opera m(ob|in)i"
//            +"|windows (phone|ce)|blackberry"
//            +"|s(ymbian|eries60|amsung)|p(laybook|alm|rofile/midp"
//            +"|laystation portable)|nokia|fennec|htc[-_]"
//            +"|mobile|up.browser|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
//
//    /**
//     * 平板浏览器USER-AGENT标识正则表达式
//     */
//    private static String tableReg = "\\b(ipad|tablet|(Nexus 7)|up.browser"+"|[1-4][0-9]{2}x[1-4][0-9]{2})\\b";
//
//    /**
//     * 手机设备正则表达式
//     */
//    private static Pattern phonePat = Pattern.compile(phoneReg, Pattern.CASE_INSENSITIVE);
//
//    /**
//     * 平板设备正则表达式
//     */
//    private static Pattern tablePat = Pattern.compile(tableReg, Pattern.CASE_INSENSITIVE);
//
//    /**
//     * 判断浏览器类型
//     * @param request request
//     * @return true-是 false-否
//     */
//    public static boolean isBrowser(HttpServletRequest request, String browserType) {
//        String userAgent = request.getHeader("User-Agent");
//        if(CommonUtil.isNotBlank(userAgent)){
//            if(userAgent.contains(browserType)){
//                return true;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否是IE浏览器
//     * @param request request
//     * @return true-是 false-否
//     */
//    public static boolean isIEBrowser(HttpServletRequest request) {
//        return isBrowser(request, "MSIE");
//    }
//
//    /**
//     * 判断是否是微信内置浏览器
//     * @param request request
//     * @return true-是 false-否
//     */
//    public static boolean isWxBrowser(HttpServletRequest request) {
//        return isBrowser(request, "MicroMessenger");
//    }
//
//    /**
//     * 获取手机机型
//     * @param request request
//     * @return 手机机型
//     */
//    public static String lookMobile(HttpServletRequest request) {
//        String userAgent = request.getHeader("User-Agent");
//        String exp = ";\\s?(\\S*?\\s?\\S*?)\\s?(Build)?/";
//        Pattern pattern = Pattern.compile(exp);
//        Matcher matcher = pattern.matcher(userAgent);
//        String model = null;
//        if(matcher.find()){
//            model = matcher.group(1).trim();
//        }
//        return model;
//    }
//
//    /**
//     * 判断是否手机浏览器
//     * @param request request
//     * @return true-手机浏览器 false-非手机浏览器
//     */
//    public static boolean isMoblieBrowser(HttpServletRequest request) {
//        boolean isMoblie = false;
//        String[] mobileAgents = { "iphone", "android", "phone", "mobile",
//                "wap", "netfront", "java", "opera mobi", "opera mini", "ucweb",
//                "windows ce", "symbian", "series", "webos", "sony",
//                "blackberry", "dopod", "nokia", "samsung", "palmsource", "xda",
//                "pieplus", "meizu", "midp", "cldc", "motorola", "foma",
//                "docomo", "up.browser", "up.link", "blazer", "helio", "hosin",
//                "huawei", "novarra", "coolpad", "webos", "techfaith",
//                "palmsource", "alcatel", "amoi", "ktouch", "nexian",
//                "ericsson", "philips", "sagem", "wellcom", "bunjalloo", "maui",
//                "smartphone", "iemobile", "spice", "bird", "zte-", "longcos",
//                "pantech", "gionee", "portalmmm", "jig browser", "hiptop",
//                "benq", "haier", "^lct", "320x320", "240x320", "176x220",
//                "w3c ", "acs-", "alav", "alca", "amoi", "audi", "avan", "benq",
//                "bird", "blac", "blaz", "brew", "cell", "cldc", "cmd-", "dang",
//                "doco", "eric", "hipt", "inno", "ipaq", "java", "jigs", "kddi",
//                "keji", "leno", "lg-c", "lg-d", "lg-g", "lge-", "maui", "maxo",
//                "midp", "mits", "mmef", "mobi", "mot-", "moto", "mwbp", "nec-",
//                "newt", "noki", "oper", "palm", "pana", "pant", "phil", "play",
//                "port", "prox", "qwap", "sage", "sams", "sany", "sch-", "sec-",
//                "send", "seri", "sgh-", "shar", "sie-", "siem", "smal", "smar",
//                "sony", "sph-", "symb", "t-mo", "teli", "tim-", "tosh", "tsm-",
//                "upg1", "upsi", "vk-v", "voda", "wap-", "wapa", "wapi", "wapp",
//                "wapr", "webc", "winw", "winw", "xda", "xda-",
//                "Googlebot-Mobile" };
//        String userAgent = request.getHeader("User-Agent");
//        if(CommonUtil.isNotBlank(userAgent)){
//            for(String mobileAgent : mobileAgents){
//                if(userAgent.toLowerCase().indexOf(mobileAgent)>=0){
//                    isMoblie = true;
//                    break;
//                }
//            }
//        }
//        return isMoblie;
//    }
//
//    /**
//     * 检测是否是移动设备访问
//     * @param userAgent 浏览器标识
//     * @return true-移动设备接入 false-非移动设备接入
//     */
//    public static boolean checkDevice(String userAgent){
//        Matcher matcherPhone = phonePat.matcher(userAgent==null ? "" : userAgent);
//        Matcher matcherTable = tablePat.matcher(userAgent==null ? "" : userAgent);
//        if(matcherPhone.find() || matcherTable.find()){
//            return true;
//        }else{
//            return false;
//        }
//    }
//
//    /**
//     * 过滤html代码
//     * @param inputString  含html标签的字符串
//     * @return
//     */
//    public static String filterHtml(String input) {
//        if ( null == input ) { return null; }
//        String htmlStr = input;
//        /**定义script正则表达式**/
//        String scriptRegex = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
//
//        /**定义style正则表达式**/
//        String styleRegex = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
//
//        /**定义HTML标签正则表达式**/
//        String htmlRegex = "<[^>]+>";
//
//        /**定义任意空白字符正则表达式**/
//        String whiteSpaceRegex = "\\s+";
//
//        /**大小写不敏感**/
//        int ignoreCase = Pattern.CASE_INSENSITIVE;
//
//        /**过滤script标签**/
//        Pattern pattern = Pattern.compile(scriptRegex, ignoreCase);
//        Matcher matcher = pattern.matcher(htmlStr);
//        htmlStr = matcher.replaceAll("");
//
//        /**过滤style标签**/
//        pattern = Pattern.compile(styleRegex, ignoreCase);
//        matcher = pattern.matcher(htmlStr);
//        htmlStr = matcher.replaceAll("");
//
//        /**过滤html标签**/
//        pattern = Pattern.compile(htmlRegex, ignoreCase);
//        matcher = pattern.matcher(htmlStr);
//        htmlStr = matcher.replaceAll("");
//
//        /**过滤任意空白字符**/
//        pattern = Pattern.compile(whiteSpaceRegex, ignoreCase);
//        matcher = pattern.matcher(htmlStr);
//        htmlStr = matcher.replaceAll("");
//
//        return htmlStr;
//    }
//
//    /**
//     * InputStream 转 String
//     * @param is 输入流
//     * @return 流转字符串
//     * @throws IOException IO异常
//     */
//    public static String convertStreamToString(InputStream is)
//            throws IOException {
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        int i;
//        while ((i = is.read()) != -1) {
//            baos.write(i);
//        }
//        return baos.toString();
//    }
//
//    /**
//     * 获取请求真实IP地址
//     * @param request HttpServletRequest对象
//     * @return 请求真实IP地址
//     */
//    public static String getRequestIP(HttpServletRequest request) {
//        int count = 0;
//        String[] ipArray = new String[20];
//        /**查直接连接的IP（可能是反向代理服务器的IP地址**/
//        if(request.getRemoteAddr() != null){
//            ipArray[count++] = request.getRemoteAddr();
//            log.info("RemoteAddr: {}", request.getRemoteAddr());
//        }
//        /**查负载均衡服务器转发IP**/
//        String ip = request.getHeader("X-Forwarded-For");
//        log.info("查负载均衡服务器转发的IP: ");
//        log.info("Header(X-Forwarded-For): {}", ip);
//        if (ip != null) {
//            if (ip.indexOf(',') < 0) {
//                /**直接IP地址**/
//                ipArray[count++] = ip;
//            } else {
//                /**如果使用代理(或是多级)从代理中取出真实IP地址**/
//                String[] ips = ip.split(",");
//                for(String ipEntry : ips){
//                    if(ipEntry == null){
//                        continue;
//                    }
//                    ipEntry = ipEntry.trim();
//                    if(ipEntry.isEmpty()){
//                        continue;
//                    }
//                    ipArray[count++] = ipEntry;
//                    if(count > (ipArray.length - 1)){
//                        break;
//                    }
//                }
//            }
//        }
//        /**得到最终的IP地址**/
//        String finalIp = null;
//        while(count >= 0){
//            ip = ipArray[count--];
//            if(ip == null){
//                continue;
//            }
//            ip = ip.trim();
//            if(ip.isEmpty()){
//                continue;
//            }
//            if(finalIp == null){
//                finalIp = ip;
//            }
//            if(isPrivateAddress(ip)){
//                continue;
//            }else{
//                finalIp = ip;
//            }
//        }
//        final String defaultIp = "0.0.0.0";
//        finalIp = finalIp != null ? finalIp :defaultIp;
//        log.info(">>>>>>客户端请求真实IP地址为({})>>>>>>", finalIp);
//        return finalIp;
//    }
//
//    /**
//     * 判断是否是内部IP地址
//     * @param ip IP地址
//     */
//    static public boolean isPrivateAddress(String ip) {
//    	final String localhostIp = "127.0.0.1";
//        if (ip.indexOf(localhostIp) >= 0) {
//            return true;
//        }
//        if (ip.startsWith("10.")) {
//            return true;
//        }
//        if (ip.startsWith("192.168.")) {
//            return true;
//        }
//        if (ip.startsWith("172.")) {
//            return isFrom16To31(ip);
//        }
//        return false;
//    }
//
//    /**
//     * 172.16.x.x至172.31.x.x 是内网ip
//     * @param ipString IP地址
//     */
//    private static boolean isFrom16To31(String ipString) {
//        boolean result = false;
//        try{
//            String[] ipArr = ipString.split("\\.");
//            int sendPart = Integer.parseInt(ipArr[1]);
//            if(sendPart >= 16 && sendPart <= 31){
//                result = true;
//            }
//        }catch(Exception e){
//            log.error("Failed to parse IP String: {} >> " + ipString, e);
//        }
//        return result;
//    }
//
//    /**
//     * 从Cookie获取用户ID
//     * @param request request
//
//    public static Long getUserId(HttpServletRequest request) {
//        Cookie[] cookies = request.getCookies();
//        if(cookies == null){
//            return null;
//        }
//        for(Cookie cookie : cookies){
//            if(ParamEnum.COOKIE_UID.value().equals(cookie.getName())) {
//                String uidStr = cookie.getValue();
//                if(StringUtils.isNumeric(uidStr)){
//                    return Long.parseLong(uidStr);
//                }
//            }
//        }
//        return null;
//    }*/
//
//    /**
//     * 获取userid
//     * @param request request
//     * @return
//     */
//    public static Long getUserId(HttpServletRequest request) {
//        String userId = (String)request.getHeader(ParamEnum.USER_ID.value());
//        log.info("userId from gateway={}", userId);
//        if(CommonUtil.isBlank(userId)){
//            return null;
//        }
//        return Long.parseLong(userId);
//    }
//
//    /**
//     * 组装Request请求参数为Map
//     * @param request HttpServletRequest
//     * @return Map
//     */
//    public static Map<String, Object> buildRequestParam(HttpServletRequest request) {
//        Map<String, Object> map = new HashMap<String, Object>();
//        Enumeration<String> paramNames = request.getParameterNames();
//        while(paramNames.hasMoreElements()){
//            String paramName = (String)paramNames.nextElement();
//            String[] paramValues = request.getParameterValues(paramName);
//            if(paramValues.length == 1){
//                String paramValue = paramValues[0];
//                if(paramValue.length() != 0) {
//                    map.put(paramName, paramValue);
//                }
//            }
//        }
//        return map;
//    }
//    /**
//     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
//     *
//     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
//     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
//     *
//     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
//     * 192.168.1.100
//     *
//     * 用户真实IP为： 192.168.1.110
//     *
//     * @param request
//     * @return
//     */
//    public static String getIpAddress(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_CLIENT_IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        String defaultIp = "0:0:0:0:0:0:0:1";
//        final String localhostIp = "127.0.0.1";
//        return ip.equals(defaultIp)?localhostIp:ip;
//    }
//
//    /**
//	 * 获取网关传递的链路TraceId
//	 * @param request HttpServletRequest
//	 * @return 网关传递的链路TraceId
//	 */
//	public static String getTraceId(HttpServletRequest request) {
//	     return request.getHeader(ParamEnum.LOG_TRACE_ID.value());
//	}
//}
