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
    private static final String TABLE_PRODUCTS = "products";
 
    // Login Table Columns nombres
    private static final String KEY_NOMBRE = "name";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_ID = "id";
    private static final String KEY_FOTO = "pic";
    private static final String KEY_SECTOR = "sector";
    private static final String KEY_PREU = "preu";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PROD_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_PRODUCTS + "("
                + KEY_ID + " INTEGER UNIQUE PRIMARY KEY NOT NULL,"
                + KEY_NOMBRE + " STRING,"
                + KEY_DESCRIPTION + " TEXT NOT NULL,"
                + KEY_FOTO + " STRING,"
                + KEY_SECTOR + " INTEGER,"
                + KEY_PREU + " INTEGER"+ ")";
        db.execSQL(CREATE_PROD_TABLE);
    }
 
    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
 
        // Create tables again
        onCreate(db);
    }

    /**
     * Storing user details in database
     * */
    public boolean addProduct(Integer id, String nombre, String description, Integer sector, Integer preu, Uri pic) {
    	SQLiteDatabase db = this.getWritableDatabase();

        //Si existeix, retorna fals, i no es pot afegir
        if (CheckExist(id)) return false;

        if ((id.equals("")) || (nombre.equals("")) || (preu.toString().equals(""))) return false;

        String foto = pic.toString();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id); // Name
        values.put(KEY_NOMBRE, nombre); // userName
        values.put(KEY_DESCRIPTION, description); // Password
        values.put(KEY_FOTO, foto); // Foto
        values.put(KEY_SECTOR, sector); // Notificacion
        values.put(KEY_PREU, preu); // Puntuacion


        // Inserting Row
        db.insert(TABLE_PRODUCTS, null, values);
        db.close(); // Closing database connection
        return true;
    }

    /**
     * Getting user data from database
     * */
    public ArrayList<Product> getAllProducts(){
        ArrayList<Product> products = new ArrayList<Product>();

        String selectQuery = "SELECT  * FROM " + TABLE_PRODUCTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        Integer i = 0;
        if(cursor.getCount() > 0){
            Product aux = new Product(cursor.getString(0));
            products.add(i, aux);
            ++i;
        }
        cursor.close();
        db.close();
        // return user
        return products;
    }

    public Boolean CheckExist(Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_ID};
        String[] where = {id};
        Cursor c = db.query(
                TABLE_PRODUCTS,
                columns,
                KEY_ID+"=?",
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

    public String getName (String user) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_NOMBRE};
        String[] where = {user};
        Cursor c = db.query(
                TABLE_PRODUCTS,
                columns,
                KEY_ID + " = ?",
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


    public boolean setFoto (Integer id, Uri path) {
        String stringUri = path.toString();
        ContentValues values = new ContentValues();
        values.put(KEY_FOTO, stringUri);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(
                TABLE_PRODUCTS,
                values,
                KEY_ID + " = '" + id + "'",
                null
        );
        db.close();
        return CheckExist(id);
    }


    public Uri getFoto (Integer id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {KEY_FOTO};
        Integer[] where = {id};
        Cursor c = db.query(
                TABLE_PRODUCTS,
                columns,
                KEY_ID + " = ?",
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
        db.delete(TABLE_PRODUCTS, null, null);
        db.close();
    }
}
