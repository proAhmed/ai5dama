package aykhadma.droidahmed.com.aykhaama.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import aykhadma.droidahmed.com.aykhaama.model.CartCheck;
import aykhadma.droidahmed.com.aykhaama.model.CartQuantity;
import aykhadma.droidahmed.com.aykhaama.model.ShoppingModel;
import aykhadma.droidahmed.com.aykhaama.model.WholeSaleModel;
import aykhadma.droidahmed.com.aykhaama.shopping_model.ShoppingProductListItem;
import aykhadma.droidahmed.com.aykhaama.wholesale_model.WholeSaleProductListItem;


/**
 * Created by ahmed on 5/14/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag

    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "contactsManager";

    // Table Names
    private static final String TABLE_ITEM = "item";
    private static final String TABLE_CART = "cart";
    private static final String TABLE_CART_ADD = "cart_add";
    private static final String TABLE_WS_CART = "ws_cart";

    // Common column names
    private static final String KEY_ID = "_id";

    // NOTES Table - column nmaes
    private static final String KEY_ITEM_ID = "id_item";
     private static final String KEY_ADD = "checks";
    private static final String KEY_ENTER = "enter";

     private static final String KEY_QUAN = "quan";
    private static final String KEY_NAME = "name";
    private static final String KEY_OLD_PRICE = "price_old";
    private static final String KEY_PRICE = "price";
    private static final String KEY_SEEN = "seen";

    private static final String KEY_PICTURE = "picture";



    // Table Create Statements
    // Todo table create statement
//    private static final String CREATE_TABLE_ITEM = "CREATE TABLE "
//            + TABLE_ITEM + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ITEM_ID
//            + " TEXT, " + KEY_ADD + " TEXT, "+ KEY_ENTER + " TEXT)";

    String CREATE_TABLE_ITEM = "CREATE TABLE item ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_item INTEGER unique, " +
            "enter INTEGER, "+
            "checks INTEGER )";

    String CREATE_TABLE_CART = "CREATE TABLE cart ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_item INTEGER unique, " +
            "quan INTEGER, "+
            "price DOUBLE ," + "price_old DOUBLE ," +
            "checks INTEGER, "+
            "seen INTEGER, "+
             "picture TEXT ,"+
    "name TEXT )";


    String CREATE_TABLE_WHOLESALE_CART = "CREATE TABLE ws_cart ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_item INTEGER unique, " +
            "quan INTEGER, "+
            "price DOUBLE ," + "price_old DOUBLE ," +
            "checks INTEGER, "+
            "seen INTEGER, "+
            "picture TEXT ,"+
            "name TEXT )";
    String CREATE_TABLE_CART_ADD = "CREATE TABLE cart_add ( " +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "id_item INTEGER unique, " +
            "quan INTEGER, "+
            "checks INTEGER, "+
            "date_cart TEXT, "+
            "name TEXT )";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    SQLiteDatabase db;
    @Override
    public void onCreate(SQLiteDatabase db) {
     //   String CREATE_TABLE_ITEM ="CREATE TABLE item (_id INTEGER PRIMARY KEY AUTOINCREMENT,id_item INTEGER, checks INTEGER, enter INTEGER)";

        // creating required tables
        db.execSQL(CREATE_TABLE_ITEM);
        db.execSQL(CREATE_TABLE_CART);
        db.execSQL(CREATE_TABLE_WHOLESALE_CART);
        db.execSQL(CREATE_TABLE_CART_ADD);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + "item");
        db.execSQL("DROP TABLE IF EXISTS " + "cart");
        db.execSQL("DROP TABLE IF EXISTS " + "cart_add");
        db.execSQL("DROP TABLE IF EXISTS " + "ws_cart");

        // create new tables
        onCreate(db);
        db.close();
    }

    /*
 * Creating a todo
 */
    public long createAdd(CartQuantity cartQuantity, int add, int enter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
          values.put(KEY_ADD, add);
        values.put(KEY_ENTER, enter);


        // insert row
        long todo_id = db.insert("item", null, values);

        // assigning tags to todo
        db.close();
        return todo_id;
    }
    public long createWholeSaleCart(WholeSaleProductListItem cartQuantity ,int quan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_QUAN, quan);
        values.put(KEY_NAME, cartQuantity.getName());
        values.put(KEY_PRICE, cartQuantity.getPrice());
        values.put(KEY_PICTURE, cartQuantity.getPicture());
//        values.put(KEY_ADD, cartQuantity.getAdded());
//        values.put(KEY_SEEN, cartQuantity.getSeen());



        // insert row
        long todo_id = db.insert("ws_cart", null, values);

        // assigning tags to todo
        db.close();

        return todo_id;
    }

    public long createCart(ShoppingProductListItem cartQuantity,int quan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_QUAN,quan);
        values.put(KEY_NAME, cartQuantity.getName());
        values.put(KEY_PRICE, cartQuantity.getPrice());
        values.put(KEY_PICTURE, cartQuantity.getPicture());
//        values.put(KEY_OLD_PRICE, cartQuantity.getOldPrice());
//        values.put(KEY_ADD, cartQuantity.getAdded());
//        values.put(KEY_SEEN, cartQuantity.getSeen());



        // insert row
        long todo_id = db.insert("cart", null, values);

        // assigning tags to todo
        db.close();

        return todo_id;
    }

    public long createCart(CartQuantity cartQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_QUAN, cartQuantity.getcQuantity());
        values.put(KEY_NAME, cartQuantity.getName());
        values.put(KEY_PRICE, cartQuantity.getPrice());
        values.put(KEY_PICTURE, cartQuantity.getPicture());
        values.put(KEY_OLD_PRICE, cartQuantity.getOldPrice());
        values.put(KEY_ADD, cartQuantity.getAdded());
        values.put(KEY_SEEN, cartQuantity.getSeen());



        // insert row
        long todo_id = db.insertWithOnConflict("cart", null, values, SQLiteDatabase.CONFLICT_IGNORE);

        // assigning tags to todo
        db.close();

        return todo_id;
    }

    public long createCartAdd(CartQuantity cartQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_QUAN, cartQuantity.getcQuantity());
        values.put(KEY_NAME, cartQuantity.getName());
        values.put(KEY_ADD, cartQuantity.getAdded());



        // insert row
        long todo_id = db.insertWithOnConflict("cart_add", null, values, SQLiteDatabase.CONFLICT_IGNORE);

        // assigning tags to todo
        db.close();

        return todo_id;
    }
    public CartCheck getItem(int item_id) {
        Cursor c = null;
        SQLiteDatabase db = null;     try {
              db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "item" + " WHERE "
                    + KEY_ITEM_ID + " = " + item_id;


             c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            CartCheck td = new CartCheck();
            assert c != null;
            td.setCartId(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
            td.setAdd((c.getInt(c.getColumnIndex(KEY_ADD))));
            td.setEnter(c.getInt(c.getColumnIndex(KEY_ENTER)));

            return td;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
            c.close();
            db.close();
        }
    }
    public CartQuantity getCartItem(int item_id) {
        Cursor c = null;
        SQLiteDatabase db = null;
        try {
              db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "cart" + " WHERE "
                    + KEY_ITEM_ID + " = " + item_id;


              c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            CartQuantity td = new CartQuantity();
            assert c != null;
            td.setID(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
            td.setcQuantity((c.getInt(c.getColumnIndex(KEY_QUAN))));
            td.setSeen((c.getInt(c.getColumnIndex(KEY_SEEN))));

            return td;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
            c.close();
            db.close();
        }
    }

    public CartQuantity getWholeSaleCartItem(int item_id) {
        Cursor c = null;
        SQLiteDatabase db = null;
        try {
            db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "ws_cart" + " WHERE "
                    + KEY_ITEM_ID + " = " + item_id;


            c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            CartQuantity td = new CartQuantity();
            assert c != null;
            td.setID(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
            td.setcQuantity((c.getInt(c.getColumnIndex(KEY_QUAN))));
            td.setSeen((c.getInt(c.getColumnIndex(KEY_SEEN))));

            return td;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
          //  c.close();
            db.close();
        }
    }
    public CartQuantity getCartItemAdd(int item_id) {
        Cursor c = null;
        SQLiteDatabase db= null;
        try {
              db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "cart_add" + " WHERE "
                    + KEY_ITEM_ID + " = " + item_id;


              c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();

            CartQuantity td = new CartQuantity();
            assert c != null;
            td.setID(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
            td.setcQuantity((c.getInt(c.getColumnIndex(KEY_QUAN))));
            td.setAdded((c.getInt(c.getColumnIndex(KEY_ADD))));

            return td;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
            c.close();
            db.close();
        }
    }



    public ArrayList<CartQuantity> getCart() {
        Cursor c = null;
        SQLiteDatabase db = null;
        try {
            ArrayList<CartQuantity> cartList = new ArrayList<>();

              db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "cart" ;


              c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();
            do {
                CartQuantity td = new CartQuantity();
            assert c != null;
            td.setID(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
                td.setcQuantity((c.getInt(c.getColumnIndex(KEY_QUAN))));
                td.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                td.setPrice(c.getDouble(c.getColumnIndex(KEY_PRICE)));
                td.setPicture(c.getString(c.getColumnIndex(KEY_PICTURE)));
                td.setOldPrice(c.getDouble(c.getColumnIndex(KEY_OLD_PRICE)));
                td.setAdded(c.getInt(c.getColumnIndex(KEY_ADD)));
                td.setSeen(c.getInt(c.getColumnIndex(KEY_SEEN)));

                cartList.add(td)    ;
            } while (c.moveToNext());
            c.close();
            db.close();
            return cartList;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
            c.close();
            db.close();
        }
    }

    public ArrayList<CartQuantity> getWholeSaleCart() {
        Cursor c = null;
        SQLiteDatabase db = null;
        try {
            ArrayList<CartQuantity> cartList = new ArrayList<>();

            db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "ws_cart" ;


            c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();
            do {
                CartQuantity td = new CartQuantity();
                assert c != null;
                td.setID(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
                td.setcQuantity((c.getInt(c.getColumnIndex(KEY_QUAN))));
                td.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                td.setPrice(c.getDouble(c.getColumnIndex(KEY_PRICE)));
                td.setPicture(c.getString(c.getColumnIndex(KEY_PICTURE)));
//              td.setOldPrice(c.getDouble(c.getColumnIndex(KEY_OLD_PRICE)));
//                td.setAdded(c.getInt(c.getColumnIndex(KEY_ADD)));
//                td.setSeen(c.getInt(c.getColumnIndex(KEY_SEEN)));

                cartList.add(td)    ;
            } while (c.moveToNext());
            c.close();
            db.close();
            return cartList;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
//            c.close();
            db.close();
        }
    }
    public ArrayList<CartQuantity> getCartAdd() {
        Cursor c = null;
        SQLiteDatabase db = null;  try {
            ArrayList<CartQuantity> cartList = new ArrayList<>();

              db = this.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + "cart_add" ;


              c = db.rawQuery(selectQuery, null);

            if (c != null)
                c.moveToFirst();
            do {
                CartQuantity td = new CartQuantity();
                assert c != null;
                td.setID(c.getInt(c.getColumnIndex(KEY_ITEM_ID)));
                td.setcQuantity((c.getInt(c.getColumnIndex(KEY_QUAN))));
                td.setName(c.getString(c.getColumnIndex(KEY_NAME)));
                td.setAdded(c.getInt(c.getColumnIndex(KEY_ADD)));

                cartList.add(td)    ;
            } while (c.moveToNext());
            db.close();
            return cartList;
        }catch (Exception e){
            return null;
        }finally {
            assert c != null;
            c.close();
            db.close();
        }
    }
    public void remove(){
        SQLiteDatabase db = this.getWritableDatabase();

         db.delete(TABLE_ITEM, null, null);
        db.close();

    }
    public void removeCart(){
        SQLiteDatabase db = this.getWritableDatabase();

         db.delete(TABLE_CART, null, null);
        db.close();

    }
    public void removeWholeSaleCart(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_WS_CART, null, null);
        db.close();

    }
    public void removeCartAdd(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_ADD, null, null);
        db.close();
    }
    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_ITEM);
        db.close();

    }
    public void deleteCart(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_CART);
        db.close();

    }

    public void deleteCartAdd(){
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("delete from "+ TABLE_CART_ADD);
        db.close();

    }
    public int updateToDo(CartQuantity cartQuantity,int add,int enter) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_ADD, add);
        values.put(KEY_ENTER, enter);


        // updating row
        return db.update("item", values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cartQuantity.getID()) });
    }
    public int updateWholeSaleCart(WholeSaleProductListItem cartQuantity, int quan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getProductID());
        values.put(KEY_QUAN, quan);


        // updating row
        return db.update("ws_cart", values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cartQuantity.getProductID()) });
    }

    public int updateCart(ShoppingProductListItem cartQuantity, int quan) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getProductID());
        values.put(KEY_QUAN, quan);


        // updating row
        return db.update("cart", values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cartQuantity.getProductID()) });
    }
    public int updateCart(CartQuantity cartQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_QUAN, cartQuantity.getcQuantity());
        values.put(KEY_SEEN, cartQuantity.getSeen());


        // updating row
        return db.update("cart", values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cartQuantity.getID()) });
    }
    public int updateCartAdd(CartQuantity cartQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ITEM_ID, cartQuantity.getID());
        values.put(KEY_QUAN, cartQuantity.getcQuantity());


        // updating row
        return db.update("cart_add", values, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cartQuantity.getID()) });
    }
    public long deleteCart(long cart_id) {
        SQLiteDatabase db = this.getWritableDatabase();
       long ff= db.delete(TABLE_CART, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cart_id) });
        db.close();
        return ff;
    }

    public long deleteWholeSaleCart(long cart_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long ff= db.delete(TABLE_WS_CART, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cart_id) });
        db.close();
        return ff;
    }
    public void deleteCartAdd(long cart_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART_ADD, KEY_ITEM_ID + " = ?",
                new String[] { String.valueOf(cart_id) });
        db.close();
    }
}
