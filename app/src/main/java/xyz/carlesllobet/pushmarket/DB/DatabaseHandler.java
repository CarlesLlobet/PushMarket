package xyz.carlesllobet.pushmarket.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import java.util.ArrayList;

import xyz.carlesllobet.pushmarket.Domain.Product;

public class DatabaseHandler extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "db";
 
    // Login table nombre
    private static final String TABLE_LOGIN = "users";
 
    // Login Table Columns nombres
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_USERNAME = "userName";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_FOTO = "foto";
    private static final String KEY_PUNT = "puntuacion";
    private static final String KEY_PUNT2 = "puntuacion2";
    private static final String KEY_NOTIF= "notificacion";
    private static final String KEY_ADDRESS= "direccion";
    private static final String KEY_TUTORIAL= "tutorial";
    private static final String KEY_TOAST= "toast";
    private static final String KEY_LNOTIF= "last_notif";
    private static final String KEY_LANG= "language";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
                + KEY_NOMBRE + " TEXT NOT NULL,"
                + KEY_USERNAME + " TEXT UNIQUE PRIMARY KEY,"
                + KEY_PASSWORD + " TEXT NOT NULL,"
                + KEY_FOTO + " STRING,"
                + KEY_NOTIF + " STRING,"
                + KEY_PUNT + " INTEGER,"
                + KEY_ADDRESS + " TEXT NOT NULL,"
                + KEY_TUTORIAL + " STRING,"
                + KEY_TOAST + " STRING,"
                + KEY_LNOTIF + " STRING,"
                + KEY_PUNT2 + " INTEGER,"
                + KEY_LANG + " STRING"+ ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
 
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public boolean addUser(String nombre, String userName, String password, String direccion, String tutorial) {
    	SQLiteDatabase db = this.getWritableDatabase();

        //Si existeix, retorna fals, i no es pot afegir
        if (CheckExist(userName)) return false;

        if ((userName.equals("")) || (password.toString().equals("")) || (direccion.toString().equals(""))) return false;


        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nombre); // Name
        values.put(KEY_USERNAME, userName); // userName
        values.put(KEY_PASSWORD, password); // Password
        values.put(KEY_FOTO, ""); // Foto
        values.put(KEY_NOTIF, "true"); // Notificacion
        values.put(KEY_PUNT, 0); // Puntuacion
        values.put(KEY_ADDRESS, direccion); // Address
        values.put(KEY_TUTORIAL, tutorial); // Tutorial
        values.put(KEY_TOAST, "true"); // Toasts
        values.put(KEY_LNOTIF, ""); // Last notif
        values.put(KEY_PUNT2, 0); // Puntuacion2
        values.put(KEY_LANG, "es"); // Idioma


        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
        return true;
    }

    public Boolean CheckExist(String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_USERNAME};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName=?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            return true;
        }
        return false;
    }

    public boolean SignIn(String user, String passwd) {
        if(!CheckExist("admin")) addUser("Administrador","admin","4dm1n","Calle de las Mariposas 21","true");
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_USERNAME,KEY_PASSWORD};
        String[] where = {user,passwd};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ? AND password = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return true;
        }
        db.close();
        return false;
    }



    public String getNotif (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_NOTIF};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getTuto (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_TUTORIAL};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getToast (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_TOAST};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getLastNotif (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_LNOTIF};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getLang (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_LANG};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "true";
    }

    public String getName (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_NOMBRE};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "";
    }

    public String getAddress (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ADDRESS};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "";
    }

    public String getPass (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PASSWORD};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return (c.getString(0));
        }
        db.close();
        return "";
    }

    public Integer getPunt (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PUNT};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return c.getInt(0);
        }
        db.close();
        return 0;
    }

    public Integer getPunt2 (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_PUNT2};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            return c.getInt(0);
        }
        db.close();
        return 0;
    }

    public boolean setNotif (String user, boolean notif) {
        ContentValues values = new ContentValues();
        String valu;
        if (notif) valu = "true";
        else valu = "false";
        values.put(KEY_NOTIF, valu);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public boolean setToast (String user, boolean toast) {
        ContentValues values = new ContentValues();
        String valu;
        if (toast) valu = "true";
        else valu = "false";
        values.put(KEY_TOAST, valu);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public boolean setTuto (String user, boolean tuto) {
        ContentValues values = new ContentValues();
        String valu;
        if (tuto) valu = "true";
        else valu = "false";
        values.put(KEY_TUTORIAL,valu);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public ArrayList<Product> getAllPuncts() {
        ArrayList<Product> productsList = new ArrayList<Product>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN + " WHERE " + KEY_PUNT2 + " <> 0  ORDER BY " + KEY_PUNT2 + " ASC";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setName(cursor.getString(0));
                product.setFoto(Uri.parse(cursor.getString(3)));
                product.setPunt(cursor.getInt(10));

                // Adding person to list
                productsList.add(product);
            } while (cursor.moveToNext());
        }
        db.close();
        // return contact list
        return productsList;
    }


    public void setPuntuacion (Integer p, String user) {
        ContentValues values = new ContentValues();
        values.put(KEY_PUNT,p);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
    }

    public boolean setLang (String user, String lang) {
        ContentValues values = new ContentValues();
        values.put(KEY_LANG,lang);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return true;
    }

    public void setPuntuacion2 (Integer p, String user) {
        ContentValues values = new ContentValues();
        values.put(KEY_PUNT2,p);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
    }

    public boolean setPass (String user, String newP) {
        ContentValues values = new ContentValues();
        values.put(KEY_PASSWORD,newP);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }

    public boolean setLastNotif (String user, String lnotif) {
        ContentValues values = new ContentValues();
        values.put(KEY_LNOTIF,lnotif);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }


    public boolean setFoto (String user, Uri path) {
        String stringUri = path.toString();
        ContentValues values = new ContentValues();
        values.put(KEY_FOTO, stringUri);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_LOGIN,
                values,
                "userName = '" + user + "'",
                null
        );
        db.close();
        return CheckExist(user);
    }


    public Uri getFoto (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_FOTO};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_LOGIN,
                columns,
                "userName = ?",
                where,
                null,
                null,
                null
        );
        if (c.moveToFirst()) {
            db.close();
            if (c.getString(0).equals("")) return null;
            else return Uri.parse(c.getString(0));
        }
        db.close();
        return null;
    }
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
}
