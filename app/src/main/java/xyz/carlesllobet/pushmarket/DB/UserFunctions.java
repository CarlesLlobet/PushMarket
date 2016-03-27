package xyz.carlesllobet.pushmarket.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
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

    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();
    }

    public JSONObject updateAllProducts(){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", products_tag));
        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(webserviceURL, params);

        return json;
    }

    public boolean loginUser(Context context, String user, String password) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res;
        //if ((!user.equals("admin"))||(!password.equals("4dm1n")))
        res = db.SignIn(user, password);
        //else res = true;
        if (res) {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("userName", user);
            editor.commit();
        }
        return res;
    }

    public String getName(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getName(username);
        return res;
    }

    /**
     * Function get Login status
     */
    public boolean isUserLoggedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains("userName")) return true;
        return false;
    }

    public String getUserName (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("userName", "");
        return res;
    }

    public String getName(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getName(username);
        return res;
    }

    public String getLang(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getLang(username);
        return res;
    }

    public String getPass(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getPass(username);
        return res;
    }

    public String getAddress(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getAddress(username);
        return res;
    }

    public Uri getFoto(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        Uri res = db.getFoto(username);
        return res;
    }

    public boolean registerUser(Context context, String nombre, String username, String password, String address, boolean tutorial) {
        DatabaseHandler db = new DatabaseHandler(context);
        String tuto = "true";
        if (!tutorial) tuto = "false";
        boolean res = db.addUser(nombre, username, password, address, tuto);
        return res;
    }

    public boolean setLang(Context context, String username, String lang) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setLang(username, lang);
        return res;
    }

    public boolean setFoto(Context context, String username, Uri image) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setFoto(username, image);
        return res;
    }

    public boolean setPass(Context context, String username, String newP) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setPass(username, newP);
        return res;
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
}
