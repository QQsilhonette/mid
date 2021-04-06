package com.tuya.hardware.symphony.dtlog.core.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 一兮
 * @Date 2021/03/15
 * @Description
 */
public class LogEsConstant {
    
    public static class DateFieldConstant {
        public static final String FROM_DATE = "fromDate";
        public static final String TO_DATE = "toDate";

        public static final Map<String, String> fieldTypeMap = new HashMap();

        static {
            fieldTypeMap.put("field_1", "text_ik");
            fieldTypeMap.put("field_2", "text_ik");
            fieldTypeMap.put("field_3", "text_ik");
            fieldTypeMap.put("field_4", "text_ik");
            fieldTypeMap.put("field_5", "text_ik");
            fieldTypeMap.put("field_6", "text");
            fieldTypeMap.put("field_7", "text");
            fieldTypeMap.put("field_8", "text");
            fieldTypeMap.put("field_9", "text");
            fieldTypeMap.put("field_10", "text");
            fieldTypeMap.put("field_11", "integer");
            fieldTypeMap.put("field_12", "integer");
            fieldTypeMap.put("field_13", "long");
            fieldTypeMap.put("field_14", "long");
            fieldTypeMap.put("field_15", "float");
            fieldTypeMap.put("field_16", "keyword");
            fieldTypeMap.put("field_17", "keyword");
            fieldTypeMap.put("field_18", "keyword");
            fieldTypeMap.put("field_19", "keyword");
            fieldTypeMap.put("field_20", "keyword");
        }
    }

    public static class SystemFieldConstant {
        public static final String form_key = "formKey";
        public static final String operator = "operator";
        public static final String platform = "platform";
        public static final String tenantId = "tenantId";
        public static final String content = "content";
        public static final String date = "date";
    }
}