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

<<<<<<< e7c63ecb9432ad349102a1a56eaf5ec6c561fe88
    public static void setToken(String tokenNovo) {
        token = tokenNovo;
    }

    public static void setId(String idNovo){
        id = idNovo;
=======
    public static void setToken(String token) {
        token = token;
    }

    public static void setId(String id){
        id =id;
>>>>>>> Classe estática, TOAD de usário ou senha inválidos. (João Soares, Marc Hermann)
    }
}
