package xyz.carlesllobet.pushmarket.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class DatabaseHandler2 extends SQLiteOpenHelper {
 
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;
 
    // Database Name
    private static final String DATABASE_NAME = "db";
 
    // Login table nombre
    private static final String TABLE_LOGIN = "users";
    private static final String TABLE_TIME = "time";
 
    // Login Table Columns nombres
    private static final String KEY_ID = "id";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_UID = "uid";
    private static final String KEY_DIRECCION = "direccion";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_FECHA_CREACION = "FECHA_CREACION";
    private static final String KEY_NIF = "nif";
    private static final String KEY_EDAD = "edad";
    private static final String KEY_PESO_INICIAL = "pesoInicial";
    
    private static final String KEY_DESPERTAR = "despertar";
    private static final String KEY_ANTES_COMER = "antesComer";
    private static final String KEY_DESPUES_COMER = "despuesComer";
    private static final String KEY_SOS = "sos";
 
    public DatabaseHandler2(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LOGIN + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_UID + " TEXT,"
                + KEY_NOMBRE + " TEXT,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_EDAD + " INTEGER,"
                + KEY_DIRECCION + " TEXT,"
                + KEY_TELEFONO + " INTEGER,"
                + KEY_PESO_INICIAL + " INTEGER,"
                + KEY_NIF + " TEXT,"
                + KEY_FECHA_CREACION + " DATETIME" + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
        
        String CREATE_TIME_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_TIME + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_UID + " TEXT,"
                + KEY_NOMBRE + " TEXT,"
                + KEY_DESPERTAR + " TEXT,"
                + KEY_ANTES_COMER + " TEXT,"
                + KEY_DESPUES_COMER + " TEXT,"
                + KEY_SOS + " TEXT"+ ")";
        db.execSQL(CREATE_TIME_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TIME);
 
        // Create tables again
        onCreate(db);
    }
 
    /**
     * Storing user details in database
     * */
    public void addUser(String uid, String nombre, String email, String edad, String direccion, String telefono,
                        String pesoInicial, String nif, String fecha_creacion) {
        
    	SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_UID, uid); // ID unico
        values.put(KEY_NOMBRE, nombre); // Name
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_EDAD, edad); //Edad
        values.put(KEY_DIRECCION, direccion); //direccion
        values.put(KEY_TELEFONO, telefono); //telefono
        values.put(KEY_PESO_INICIAL, pesoInicial); // NIF/NIE
        values.put(KEY_NIF, nif); // NIF/NIE
        values.put(KEY_FECHA_CREACION, fecha_creacion); // Created At

 
        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }
    
    /**
     * Storing time details in database
     * */
    public void addTime(String uid, String nombre, String despertar, String antesComer, String despuesComer, String sos) {
        
    	SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, nombre); // Name
        values.put(KEY_UID, uid); // ID unico
        values.put(KEY_DESPERTAR, despertar); // Hora entrada
        values.put(KEY_ANTES_COMER, antesComer); // Hora sortida
        values.put(KEY_DESPUES_COMER, despuesComer); // Hora sortida
        values.put(KEY_SOS, sos); // Hora sortida
 
        // Inserting Row
        db.insert(TABLE_TIME, null, values);
        db.close(); // Closing database connection
    }
    
    public HashMap<String, String[]> getTimeDetails(){
        HashMap<String,String[]> time = new HashMap<String,String[]>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_TIME;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        
        String[] uid = new String[50];
        String[] nombre = new String[50];
        String[] despertar = new String[50];
        String[] antesComer = new String[50];
        String[] despuesComer = new String[50];
        String[] sos = new String[50];
        
        int i = 0;
        // Move to first row
        cursor.moveToFirst();
        while (i < cursor.getCount() && i < 50) {
        	uid[i] = cursor.getString(1);
        	nombre[i] = cursor.getString(2);
        	despertar[i] = cursor.getString(3);
        	antesComer[i] = cursor.getString(4);
            despuesComer[i] = cursor.getString(5);
            sos[i] = cursor.getString(6);

        	cursor.moveToNext();
        	++i;
        }
        cursor.close();
        db.close();
        
        String[] numero = {String.valueOf(i)};
        // return user
        time.put("uid", uid);
        time.put("nombre", nombre);
        time.put("despertar", despertar);
        time.put("antesComer", antesComer);
        time.put("despuesComer", despuesComer);
        time.put("sos", sos);
        time.put("numero", numero);
        return time;
    }
    
    /**
     * Getting user data from database
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;
          
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("uid", cursor.getString(1));
            user.put("nombre", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("edad", cursor.getString(4));
            user.put("direccion", cursor.getString(5));
            user.put("telefono", cursor.getString(6));
            user.put("pesoInicial", cursor.getString(7));
            user.put("nif", cursor.getString(8));
            user.put("fecha_creacion", cursor.getString(9));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }
 
    /**
     * Getting user login status
     * return true if rows are there in table
     * */
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
         
        // return row count
        return rowCount;
    }
     
    /**
     * Re crate database
     * Delete all tables and create them again
     * */
    public void resetTables(){
        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }
 
}
