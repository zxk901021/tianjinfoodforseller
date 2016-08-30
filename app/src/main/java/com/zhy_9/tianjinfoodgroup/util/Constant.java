package com.zhy_9.tianjinfoodgroup.util;

import com.zhy_9.tianjinfoodgroup.encrypt.EncryptParams;

/**
 * Created by ZHY_9 on 2016/8/30.
 */
public class Constant {

    public static final String salt = EncryptParams.md5(EncryptParams.sha1("zhihuiyun"));
}
