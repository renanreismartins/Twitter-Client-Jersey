package br.com.tobrocado.twitterclient;

public class Settings {

    public static final String FRIENDS_TIMELINE_URL = "http://api.twitter.com/1/statuses/friends_timeline.xml";

    public static final String CONSUMER_KEY = "2clph6GDtKfkJOI9FfMA";
    public static final String CONSUMER_SECRET = "BhyskaFTEXN8KvLtJETxnEIOffIwXy398a8IydlI";
    public static final String REQUEST_TOKEN_URL = "http://twitter.com/oauth/request_token";
    public static final String ACCESS_TOKEN_URL = "http://twitter.com/oauth/access_token";
    public static final String AUTHORIZE_URL = "http://twitter.com/oauth/authorize";
    private static String token;
    private static String tokenSecret;
    private static boolean authorized;

    public static void setToken(String token, String tokenSecret, boolean authorized) {
        Settings.token = token;
        Settings.tokenSecret = tokenSecret;
        Settings.authorized = authorized;
    }

    public static String getToken() {
        return token;
    }

    public static String getTokenSecret() {
        return tokenSecret;
    }

    public static boolean isAuthorized() {
        return authorized;
    }
}
