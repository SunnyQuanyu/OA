package cn.lcl.util;

public class WxAccessToken {

    private static String accessToken = null;

    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        WxAccessToken.accessToken = accessToken;
    }
}
