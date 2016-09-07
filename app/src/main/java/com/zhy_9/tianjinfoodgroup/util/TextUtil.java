package com.zhy_9.tianjinfoodgroup.util;

/**
 * Created by ZHY_9 on 2016/9/7.
 */
public class TextUtil {

    public static final String setNullToEmpty(String str){
        if (str == null) {
            str = "";
        }
        return str;
    }
}
