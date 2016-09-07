package com.zhy_9.tianjinfoodgroup.util;

/**
 * Created by ZHY_9 on 2016/8/30.
 */
public class UrlUtil {
    public static final String BASE_URL = "http://192.168.18.102/";
    public static final String USER_LOGIN = BASE_URL + "shipin/index.php/WapApp/user/user_login";
    public static final String SEND_VERIFY_CODE = BASE_URL + "shipin/index.php/WapApp/user/send_user_verify";
    public static final String SET_NEW_PASSWORD = BASE_URL + "shipin/index.php/WapApp/user/reset_password";
    public static final String ORDER_COUNTS_DATA = BASE_URL + "shipin/index.php/WapApp/order/get_order_num";
    public static final String MALL_MESSAGE_COUNT = BASE_URL + "shipin/index.php/WapApp/shop/shop_message_num";

}
