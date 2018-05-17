package ages181.policiafederal_android;

public class token {

    private static String Token;

    public static String getToken() {
        return Token;
    }

    public static void setToken(String token) {
        Token = token;
    }

    public token(String token) {

        Token = token;
    }
}
