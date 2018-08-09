package com.xuehai.utils;


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

/**
 * <pre>
 * Copyright:		Copyright(C) 2012-2015
 * Class:			JsonUtil
 * Date:			2017/3/2
 * Version          1.1.0
 * Description:		json过度接口 方便切换其他json方式
 * </pre>
 **/
@Slf4j
public class JsonUtil {

    public static String toJSON(Object obj) {
        return JSON.toJSONString(obj);
    }


    public static <T> T parseObject(String text, Class<T> clazz) {
        return JSON.parseObject(text, clazz);
    }

    public static <T> List<?> parseArray(String text, Class<?> clazz) {
        return JSON.parseArray(text, clazz);
    }

    public static Map<String, Object> jsonToMap(String json) {
        return JSON.parseObject(json, Map.class);
    }

    public static Map<String, Object> objectToMap(Object obj) {
        return JSON.parseObject(toJSON(obj), Map.class);
    }

}
