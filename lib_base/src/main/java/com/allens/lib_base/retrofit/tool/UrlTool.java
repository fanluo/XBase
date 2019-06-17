package com.allens.lib_base.retrofit.tool;

import java.util.Map;

public class UrlTool {

    //拼接URL
    public static String prepareParam(Map<String, Object> paramMap) {
        StringBuilder sb = new StringBuilder();
        if (paramMap.isEmpty()) {
            return "";
        } else {
            for (String key : paramMap.keySet()) {
                Object value = paramMap.get(key);
                if (sb.length() < 1) {
                    sb.append(key).append("=").append(value);
                } else {
                    sb.append("&").append(key).append("=").append(value);
                }
            }
            return sb.toString();
        }
    }
}
