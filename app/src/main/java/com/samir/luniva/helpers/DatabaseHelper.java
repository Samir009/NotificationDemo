package com.samir.luniva.helpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import com.samir.luniva.models.AddToCardModel;
import com.samir.luniva.models.UserModel;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * created by SAMIR SHRESTHA on 1/30/2020  at 3:49 PM
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "student.db";

//    names of table
    public static final String TABLE_NAME = "student_table";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "ADDRESS";
    public static final String COL_4 = "USER_IMAGE";

    public static final String TBL_ADDED_TO_CART = "tbl_added_to_cart";

    public static final String AC_ID = "ID";
    public static final String AC_CLINIC_ID = "CLINIC_ID";
    public static final String AC_TEST_ID = "TEST_ID";
    public static final String AC_PRICE = "TEST_PRICE";
    public static final String AC_TOTAL_PRICE = "TEST_TOTAL_PRICE";
    public static final String AC_DISPLAY_NAME = "TEST_DISPLAY_NAME";
    public static final String AC_CREATED_ON = "TEST_CREATED_ON";


//    to store image in it
    private ByteArrayOutputStream objectByteArrayOutputStream;
    private byte[] imageInBytes;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
        Log.e("DatabaseHelper: ", "dbhelper constructor called");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT, ADDRESS TEXT,USER_IMAGE BLOB)");
        db.execSQL("create table " + TBL_ADDED_TO_CART + "(ID INTEGER PRIMARY KEY, CLINIC_ID INTEGER,TEST_ID INTEGER," +
                "TEST_PRICE DOUBLE, TEST_TOTAL_PRICE DOUBLE, TEST_DISPLAY_NAME TEXT, TEST_CREATED_ON TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.e("onUpgrade: ", "onupgrade callled");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ADDED_TO_CART);
        onCreate(db);
    }

    public void insertUserData(UserModel userModel){
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap imageToStore = userModel.getImg();

            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStore.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageInBytes = objectByteArrayOutputStream.toByteArray();

            ContentValues cv = new ContentValues();
            cv.put(COL_2, userModel.getName());
            cv.put(COL_3, userModel.getAddress());
            cv.put(COL_4, imageInBytes);

            long result = db.insert(TABLE_NAME, null, cv);

            if(result > -1){
                ShowToast.withMessage("Data inserted successfully");
                db.close();
            } else {
                ShowToast.withMessage("!!! data not inserted !!!");
            }

        } catch (Exception e){
            Log.e("insertUserData: ",e.getMessage() );
        }
    }
//    public boolean insertData(String name, String address){
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(COL_2, name);
//        cv.put(COL_3, address);
//
//        long result = db.insert(TABLE_NAME, null, cv);
//
//        if(result == -1)
//            return false;
//        else
//            return true;
//    }

//    public Cursor getAllData(){
//        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
//        return res;
//    }

    public boolean updateData(String id, UserModel userModel){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
//        contentValues.put(COL_1, id);
        contentValues.put(COL_2, userModel.getName());
        contentValues.put(COL_3, userModel.getAddress());
        contentValues.put(COL_4, imageInBytes);
        db.update(TABLE_NAME, contentValues, "ID = ?",new String[] {id});
        return true;
    }

    public Integer deleteData(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, "ID = ?", new String[] {id});
    }

    public ArrayList<UserModel> getAllData(){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ArrayList<UserModel> userModelArrayList = new ArrayList<>();

            Cursor objCursor = db.rawQuery("select * from "+ TABLE_NAME, null);
            if(objCursor.getCount() != 0){
                while (objCursor.moveToNext()){
                    String name = objCursor.getString(1);
                    String address = objCursor.getString(2);
                    byte[] imageBytes = objCursor.getBlob(3);

                    Bitmap objBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);

                    userModelArrayList.add(new UserModel(name, address, objBitmap));
                }
                Log.e("getAllData: ",userModelArrayList.toString());
                return userModelArrayList;
            } else{
                ShowToast.withMessage("No data");
                return null;
            }
        } catch(Exception ex){
            Log.e("getAllData: ",ex.getMessage() );
            return null;
        }
    }

//    ========================================================= add to cart ======================================

    public void addToCart(AddToCardModel addToCardModel){

        try {
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            Log.e( "addToCart: ", "add to cart sqlite hit");

            ContentValues contentValues = new ContentValues();
            contentValues.put(AC_ID, addToCardModel.getId());
            contentValues.put(AC_CLINIC_ID, addToCardModel.getClinic_id());
            contentValues.put(AC_TEST_ID, addToCardModel.getTest_id());
            contentValues.put(AC_PRICE, addToCardModel.getPrice());
            contentValues.put(AC_TOTAL_PRICE, addToCardModel.getTotal_price());
            contentValues.put(AC_DISPLAY_NAME, addToCardModel.getDisplay_name());
            contentValues.put(AC_CREATED_ON, addToCardModel.getCreated_on());

            long result = sqLiteDatabase.insertWithOnConflict(TBL_ADDED_TO_CART,  null, contentValues, SQLiteDatabase.CONFLICT_REPLACE);

            if(result > -1){
//                ShowToast.withLongMessage("Data inserted successfully :)");
                sqLiteDatabase.close();
            } else {
                ShowToast.withLongMessage("Data not inserted !!!");
            }

        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public ArrayList<AddToCardModel> getAddedToCart(){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

            ArrayList<AddToCardModel> addToCardModels = new ArrayList<>();

            Cursor cursor = sqLiteDatabase.rawQuery("select * from " + TBL_ADDED_TO_CART, null);

//            Log.e("getAddedToCart: ", new GsonBuilder().create().toJson(cursor));

            if(cursor.getCount() != 0){
                Log.e("getAddedToCart: ","some data are in db" );

                while(cursor.moveToNext()){
                    int id = cursor.getInt(0);
                    int clinic_id = cursor.getInt(1);
                    int test_id = cursor.getInt(2);
                    double price = cursor.getDouble(3);
                    double total_price = cursor.getDouble(4);
                    String display_name = cursor.getString(5);
                    String created_on = cursor.getString(6);



                    Log.e( "getAddedToCart: ","id of added to cart " + id);

                    Log.e("getAddedToCart: ", clinic_id + "\t" + test_id + "\t" + price + "\t" + total_price +
                            "\t" + display_name + "\t" + created_on);
                    addToCardModels.add(new AddToCardModel(id, clinic_id, test_id, price, total_price, display_name, created_on));
                }
                return addToCardModels;
            } else {
                ShowToast.withLongMessage("No data found");
                return null;
            }

        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean removeFromCart(int id){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.delete(TBL_ADDED_TO_CART, "id = ?", new String[]{String.valueOf(id)});
            return true;
        } catch(Exception ex){
            ex.printStackTrace();
            return false;
        }
    }

    public boolean removeAllFromCart(){
        try{
            SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
            sqLiteDatabase.execSQL("delete from " + TBL_ADDED_TO_CART);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

}
