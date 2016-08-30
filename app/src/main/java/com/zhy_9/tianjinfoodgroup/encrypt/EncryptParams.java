package com.zhy_9.tianjinfoodgroup.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by ZHY_9 on 2016/8/30.
 */
public class EncryptParams {

    public static String md5(String md5) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(md5.getBytes());
            byte[] md = digest.digest();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                String shaHex = Integer.toHexString(md[i] & 0xFF);
                if (shaHex.length() < 2) {
                    buffer.append(0);

                }
                buffer.append(shaHex);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String sha1(String params) {
        byte[] _bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(params.getBytes());
            _bytes = md.digest();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < _bytes.length; i++) {
                String shaHex = Integer.toHexString(_bytes[i] & 0xFF);
                if (shaHex.length() < 2) {
                    buffer.append(0);
                }
                buffer.append(shaHex);
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "";
    }

    public static String getString(Map<String, String> params) {
        Iterator i = params.entrySet().iterator();
        ArrayList<String> keys = new ArrayList<>();
        StringBuffer buffer = new StringBuffer();
        while (i.hasNext()) {
            Object o = i.next();
            String key = o.toString();
            keys.add(key);
        }
        Collections.sort(keys);
        for (int j = 0; j < keys.size(); j++) {
            buffer.append(keys.get(j)).append("&");
        }
        StringBuffer temp = buffer.deleteCharAt(buffer.length() - 1);
        return temp.toString().toUpperCase();

    }

}
