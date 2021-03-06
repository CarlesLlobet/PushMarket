package xyz.carlesllobet.pushmarket.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import xyz.carlesllobet.pushmarket.Domain.Product;
import xyz.carlesllobet.pushmarket.R;

public class UserFunctions {

    private JSONParser jsonParser;

    // Testing in localhost using wamp or xampp
    // use http://10.0.2.2/ to connect to your localhost ie http://localhost/

    //private static String loginURL = "http://pushmarket.carlesllobet.xyz/webservice/login.php";
    //private static String registerURL = "http://pushmarket.carlesllobet.xyz/webservice/register.php";
    private static String webserviceURL = "http://pushmarket.carlesllobet.xyz/webservice/index.php";

    private static String login_tag = "login";
    private static String register_tag = "register";
    private static String products_tag = "products";
    private static String modify_tag = "modifyUser";

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
                                   String sexe, String pais) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", register_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("nom", nombre));
        params.add(new BasicNameValuePair("cognoms", cognoms));
        params.add(new BasicNameValuePair("data_naix", edad));
        params.add(new BasicNameValuePair("sexe", sexe));
        params.add(new BasicNameValuePair("pais", pais));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(webserviceURL, params);

        return json;
    }

    public JSONObject modifyUser(String email, String nombre, String cognoms, String edad, String password,
                                   String sexe, String pais) {
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", modify_tag));
        params.add(new BasicNameValuePair("email", email));
        params.add(new BasicNameValuePair("nom", nombre));
        params.add(new BasicNameValuePair("cognoms", cognoms));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("data_naix", edad));
        params.add(new BasicNameValuePair("sexe", sexe));
        params.add(new BasicNameValuePair("pais", pais));

        // getting JSON Object
        JSONObject json = jsonParser.getJSONFromUrl(webserviceURL, params);

        return json;
    }

    public String getName (Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("nom", "");
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
        String res = preferences.getString("cognoms", "");
        return res;
    }

    public String getAge(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("data_naix", "");
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

    public String getLang(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String res = preferences.getString("language", "");
        return res;
    }

    public void setLang(Context context, String lang) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("language", lang);
        editor.commit();
    }

    public void setPassword (Context context, String password){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("password", password);
        editor.commit();
    }

    /**
     * Function get Login status
     */
    public boolean isUserLoggedIn(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.contains("email")) return true;
        return false;
    }

    /**
     * Function to logout user
     * Reset Database
     */
    public void logoutUser(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.remove("email");
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

    public Product getProduct(Context context, Long id) {
        DatabaseHandler db = new DatabaseHandler(context);
        Product product = db.getProduct(id);
        return product;
    }

    public boolean addProduct(Context context, Long id, String nom, String descripcio, Double preu, Integer sector, Uri foto){
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.addProduct(id,nom,descripcio,sector,preu,foto);
        return res;
    }

    public Boolean checkTestValues(Context context){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString("TestValues","notExists").equals("notExists")) {
            //Fiquem valors inicials
            Uri llet = Uri.parse("android.resource://xyz.carlesllobet.pushmarket/" + R.mipmap.llet);
            Uri xampu = Uri.parse("android.resource://xyz.carlesllobet.pushmarket/" + R.mipmap.xampu);
            Uri dentifric = Uri.parse("android.resource://xyz.carlesllobet.pushmarket/" + R.mipmap.dentifric);
            Uri patates = Uri.parse("android.resource://xyz.carlesllobet.pushmarket/" + R.mipmap.patates);
            Uri cafe = Uri.parse("android.resource://xyz.carlesllobet.pushmarket/" + R.mipmap.cafe);
            addProduct(context, 3057067222903L, "Llet Puleva", "Sense lactosa", 1.25, 2, llet);
            addProduct(context, 4084500272088L, "Xampú H&S", "Anticaspa", 2.80, 32, xampu);
            addProduct(context, 5411786006905L, "Cafè Marcilla", "Barreja", 2.29, 2, cafe);
            addProduct(context,8410372152306L, "Pasta Colgate", "Anticàries", 1.55, 33, dentifric);
            addProduct(context,410199000781L, "Patates Xip Lay's", "Gourmet cruixents", 1.99, 1, patates);

            //Guardem que s'han posat els valors inicials
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("TestValues", "inserted");
            editor.commit();
            return false;
        } else return true;
    }
}
