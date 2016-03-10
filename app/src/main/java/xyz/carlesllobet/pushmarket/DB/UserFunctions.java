package xyz.carlesllobet.pushmarket.DB;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import java.util.ArrayList;

import xyz.carlesllobet.pushmarket.Domain.Person;

public class UserFunctions {


    /**
     * Function to know if user has admin permissions
     */

    public boolean isUserAdmin(Context context) { //S'ha de fer amb SharedPreferences
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (preferences.getString("userName", "").equals("admin")) return true;
        return false;
    }

    /**
     * Function to know if user has admin permissions
     */

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

    public boolean getNotif(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getNotif(username);
        return res.equals("true");
    }

    public boolean getTuto(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getTuto(username);
        return res.equals("true");
    }

    public boolean getToast(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getToast(username);
        return res.equals("true");
    }

    public String getLastNotif(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        String res = db.getLastNotif(username);
        return res;
    }

    public Integer getPunt(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        Integer res = db.getPunt(username);
        return res;
    }

    public Integer getPunt2(Context context, String username) {
        DatabaseHandler db = new DatabaseHandler(context);
        Integer res = db.getPunt2(username);
        return res;
    }

    public boolean registerUser(Context context, String nombre, String username, String password, String address, boolean tutorial) {
        DatabaseHandler db = new DatabaseHandler(context);
        String tuto = "true";
        if (!tutorial) tuto = "false";
        boolean res = db.addUser(nombre, username, password, address, tuto);
        return res;
    }

    public boolean setNotif(Context context, String username, boolean notif) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setNotif(username, notif);
        return res;
    }

    public boolean setLang(Context context, String username, String lang) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setLang(username, lang);
        return res;
    }

    public boolean setTuto(Context context, String username, boolean tuto) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setTuto(username, tuto);
        return res;
    }

    public boolean setToast(Context context, String username, boolean toast) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setToast(username, toast);
        return res;
    }

    public boolean setLastNotif(Context context, String username, String lnotif) {
        DatabaseHandler db = new DatabaseHandler(context);
        boolean res = db.setLastNotif(username, lnotif);
        return res;
    }

    public void setPunt(Context context, String username, int punt) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.setPuntuacion(punt, username);
    }

    public void setPunt2(Context context, String username, int punt) {
        DatabaseHandler db = new DatabaseHandler(context);
        db.setPuntuacion2(punt, username);
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

    public ArrayList<Person> getAllPunts(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Person> llista = db.getAllPuncts();
        return llista;
    }

    public ArrayList<Person> getAll2Punts(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        ArrayList<Person> llista = db.getAll2Puncts();
        return llista;
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
