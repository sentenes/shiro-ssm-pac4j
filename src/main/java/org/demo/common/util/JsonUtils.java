package org.demo.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.ObjectUtils.Null;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

public class JsonUtils {
    private static Class that = JsonUtils.class;

    public static <T> T jsonToObjUseGson(String json, Class<T> requiredType) {
        if (json == null || StringUtils.isBlank(json) || json.equals("{}")) {
            LoggerUtils.error(that, "json转换对象失败,json字符串为空");
            return null;
        }
        T t = null;
        try {
            Gson gson = new Gson();
            t = gson.fromJson(json, requiredType);
        } catch (Exception e) {
            LoggerUtils.fmtError(that, e, "Parse json string to object by Gson error.[json: %s,class : %s]", json, requiredType.getName());
        }
        return t;
    }

    public static <T> T jsonToObj(String json, Class<T> requiredType) {
        if (json == null || StringUtils.isBlank(json) || json.equals("{}")) {
            LoggerUtils.error(that, "json转换对象失败,json字符串为空");
            return null;
        }
        T t = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            t = mapper.readValue(json, requiredType);
        } catch (Exception e) {
            LoggerUtils.fmtError(that, e, "Parse json string to object error. [json : %s, class : %s]", json, requiredType.getName());
        }
        return t;
    }

    public static String objToJson(Object obj) {
        if (obj == null) {
            LoggerUtils.fmtError(that, "Parse object to json string failed. [obj:%s]", obj);
            return null;
        }
        String json = "";
        try {
            Gson gson = new Gson();
            json = gson.toJson(obj);
        } catch (Exception e) {
            LoggerUtils.fmtError(that, "Parse Object to Json String error.obj:%s", obj.toString());
        }
        return json;

    }
}
