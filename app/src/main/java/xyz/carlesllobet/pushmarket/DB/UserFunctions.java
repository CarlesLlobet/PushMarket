package xyz.carlesllobet.pushmarket.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.carlesllobet.pushmarket.Domain.Product;

public class UserFunctions {

    private JSONParser jsonParser;

    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/

    //private static String loginURL = "http://192.168.69.18/servei_web";
    //private static String registerURL = "http://192.168.69.18/servei_web";
    private static String webserviceURL = "http://pushmarket.carlesllobet.xyz/webservice/index.php";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String products_tag = "products";
    private static String password_tag = "password";

    private SharedPreferences preferences;

    // constructor
    public UserFunctions() {
        jsonParser = new JSONParser();
    }

    //USERS

    public JSONObject loginUser(String email, String password) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", login_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));

        JSONObject json = jsonParser.getJSONFromUrl(webserviceURL, params);

        return json;
    }

    public JSONObject registerUser(String email, String nombre, String cognoms, String edad, String password,
                                   String sexe, String pais, String ciutat) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("nombre", nombre));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("cognoms", cognoms));
        params.add(new BasicNameValuePair("edad", edad));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("sexe", sexe));
        params.add(new BasicNameValuePair("pais", pais));
        params.add(new BasicNameValuePair("ciutat", ciutat));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(webserviceURL, params);

        return json;
    }



    public String getName (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("name", "");
        return res;
    }

    public String getPass (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("password", "");
        return res;
    }

    public String getLang (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("language", "");
        return res;
    }

    public String getEmail(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("email", "");
        return res;
    }

    public String getPassword(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("password", "");
        return res;
    }

    public String getLastName(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("lastName", "");
        return res;
    }

    public Integer getAge(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        Integer res = preferences.getInt("edad", 0);
        return res;
    }

    public String getSex(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("sexe", "");
        return res;
    }

    public String getCountry(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("pais", "");
        return res;
    }

    public String getCity(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("ciutat", "");
        return res;
    }

    public void setLang(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", lang);
    }

    public void setPass (Context context, String password){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);

        //SET PASSWORD TO WEBSERVICE DB
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", password_tag));
        params.add(new BasicNameValuePair("password", password));

        // getting JSON Object
        jsonParser.getJSONFromUrl(webserviceURL, params);
    }

    /**
     * Function get Login status
     */
    public boolean isUserLoggedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains("userName")) return true;
        return false;
    }

    /**
     * Function to logout user
     * Reset Database
     */
    public void logoutUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove("userName");
        editor.commit();
    }


    //PRODUCTS

    public void updateAllProducts(Context context) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", products_tag));
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(webserviceURL, params);

        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTable();
        //Insert all products of the json
    }

    public ArrayList<Product> getAllProducts(Context context) {
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Product> products = db.getAllProducts();
        return products;
    }

    public String getProductName(Context context, Integer id) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getProductName(id);
        return res;
    }
}
