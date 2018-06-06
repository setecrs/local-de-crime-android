package ages181.policiafederal_android;

public class StaticProperties {

    private static String url = "http://ages-pf.herokuapp.com/";
    private static String token;
    private static String id;

    public static String getId(){
        return id;
    }

    public static String getUrl() {
        return url;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String tokenNovo) {
        token = tokenNovo;
    }

    public static void setId(String idNovo){
        id = idNovo;
    }
}
