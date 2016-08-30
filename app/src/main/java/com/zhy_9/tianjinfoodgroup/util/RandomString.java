package com.zhy_9.tianjinfoodgroup.util;

import java.util.Random;

/**
 * Created by ZHY_9 on 2016/8/30.
 */
public class RandomString {

    public static String getRandomString(int length){
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < length ; i ++){
            int number = random.nextInt(62);
            buffer.append(str.charAt(number));
        }
        return buffer.toString();
    }
}
