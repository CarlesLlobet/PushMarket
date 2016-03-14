package xyz.carlesllobet.pushmarket.DB;

import android.content.Context;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserFunctions2 {
     
    private JSONParser jsonParser;
     
    // Testing in localhost using wamp or xampp 
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/
    
    //private static String loginURL = "http://192.168.69.18/servei_web";
    //private static String registerURL = "http://192.168.69.18/servei_web";
    private static String loginURL = "http://carlesllobet.xyz/webservice/index.php";
	private static String registerURL = "http://carlesllobet.xyz/webservice/index.php";
	private static String despertarURL = "http://carlesllobet.xyz/webservice/index.php";
	private static String antesComerURL = "http://carlesllobet.xyz/webservice/index.php";
	private static String despuesComerURL = "http://carlesllobet.xyz/webservice/index.php";
    private static String sosURL = "http://carlesllobet.xyz/webservice/index.php";
    private static String testURL = "http://carlesllobet.xyz/webservice/index.php";
     
    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String despertar_tag = "despertar";
    private static String antesComer_tag = "antesComer";
    private static String despuesComer_tag = "despuesComer";
    private static String sos_tag = "sos";
    private static String test_tag = "test";
     
    // constructor
    public UserFunctions2(){
        jsonParser = new JSONParser();
    }
    
    public JSONObject despertar(Context context){
    	DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String,String> usuari = db.getUserDetails();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", despertar_tag));
        params.add(new BasicNameValuePair("uuid", usuari.get("uid")));
        params.add(new BasicNameValuePair("nombre", usuari.get("nombre")));
        
        JSONObject json = jsonParser.getJSONFromUrl(despertarURL, params);

        return json;
    }
    
    public JSONObject antesComer(Context context){
    	DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String,String> usuari = db.getUserDetails();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", antesComer_tag));
        params.add(new BasicNameValuePair("uuid", usuari.get("uid")));
        params.add(new BasicNameValuePair("nombre", usuari.get("nombre")));
        
        JSONObject json = jsonParser.getJSONFromUrl(antesComerURL, params);

        return json;
    }

    public JSONObject despuesComer(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String,String> usuari = db.getUserDetails();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", despuesComer_tag));
        params.add(new BasicNameValuePair("uuid", usuari.get("uid")));
        params.add(new BasicNameValuePair("nombre", usuari.get("nombre")));

        JSONObject json = jsonParser.getJSONFromUrl(despuesComerURL, params);

        return json;
    }

    public JSONObject sos(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String,String> usuari = db.getUserDetails();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", sos_tag));
        params.add(new BasicNameValuePair("uuid", usuari.get("uid")));
        params.add(new BasicNameValuePair("nombre", usuari.get("nombre")));

        JSONObject json = jsonParser.getJSONFromUrl(sosURL, params);

        return json;
    }

    public JSONObject test(Context context, String hambre){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String,String> usuari = db.getUserDetails();
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", test_tag));
        params.add(new BasicNameValuePair("uuid", usuari.get("uid")));
        params.add(new BasicNameValuePair("nombre", usuari.get("nombre")));
        params.add(new BasicNameValuePair("hambre", hambre));

        JSONObject json = jsonParser.getJSONFromUrl(testURL, params);

        return json;
    }
     
    /**
     * function make Login Request
     * @param email
     * @param password
     * */
    public JSONObject loginUser(String email, String password){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);

        return json;
    }
     
    /**
     * function make Register Request
     * @param nombre
     * @param email
     * @param password
     * @param direccion
     * @param nif
     * @param pesoInicial
     * @param telefono
     * */
    public JSONObject registerUser(String nombre, String email, String edad, String password,
    		String direccion, String nif, String pesoInicial, String telefono){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("nombre", nombre));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("edad", edad));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("direccion", direccion));
        params.add(new BasicNameValuePair("telefono", telefono));
        params.add(new BasicNameValuePair("pesoInicial", pesoInicial));
        params.add(new BasicNameValuePair("nif", nif));
         
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);

        return json;
    }

    /**
     * Function to know if user has admin permissions
     * */
    
    public boolean isUserAdmin(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String,String> usuari = db.getUserDetails();

        if (usuari.get("email").equals("admin@admin.com")){
            // admin user
            return true;
        }
        return false;
    }
     
    /**
     * Function get Login status
     * */
    public boolean isUserLoggedIn(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        int count = db.getRowCount();
        if(count > 0){
            // user logged in
            return true;
        }
        return false;
    }

    public String getEmail(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("email");
    }

    public String getName(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("nombre");
    }

    public String getNIF(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("nif");
    }

    public String getAddress(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("direccion");
    }

    public String getPhone(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("telefono");
    }

    public String getAge(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("edad");
    }

    public String getPesoInicial(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        HashMap<String, String> user = db.getUserDetails();
        return user.get("pesoInicial");
    }


     
    /**
     * Function to logout user
     * Reset Database
     * */
    public boolean logoutUser(Context context){
        DatabaseHandler2 db = new DatabaseHandler2(context);
        db.resetTables();
        return true;
    }
     
}
