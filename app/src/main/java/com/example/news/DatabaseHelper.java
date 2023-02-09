package com.example.news;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.widget.Toast;
import android.os.Bundle;

import androidx.annotation.Nullable;

import java.io.File;

public class DatabaseHelper extends SQLiteOpenHelper {

  //  public static final String FILE_DIR = "DATABASE";
    public static final String DATABASE_NAME  = "Master.db";
    public static final String TABLE_NAME= "DATA";
    public static final String ColumnID = "user_id";
    public static final String Column_2 = "name";
    public static final String Column_3 = "dateofbirth";
    public static final String Column_4 = "email";
    public static final String Column_5 = "password";
    public static final String Column_6 = "phoneno";
    public static final String Column_7 = "collegename";

    public DatabaseHelper(@Nullable Context context)
    {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {

        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + ColumnID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Column_2 + " TEXT,"+ Column_3 +" TEXT," + Column_4 +" TEXT,"+ Column_5 +" TEXT,"+ Column_6 +" TEXT,"+ Column_7 +" TEXT)";
        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String name,String dob,String email,String pass,String phone,String college)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cv= new ContentValues();
            cv.put(Column_2,name);
            cv.put(Column_3,dob);
            cv.put(Column_4,email);
            cv.put(Column_5,pass);
            cv.put(Column_6,phone);
            cv.put(Column_7,college);

            long result = db.insert(TABLE_NAME,null,cv);

            if (result == -1)
                return  false;
            else
                return  true;
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            return  false;
        }
    }
    public boolean checkdata(String email,String pass)
    {
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
      //      Cursor cursor = db.rawQuery(" SELECT ColumnID FROM " + TABLE_NAME + " WHERE " + Column_4 + " = ' " + email + " ' " + "  AND " + Column_5 + " = ' " + pass + " ' ",null);
            String[] columns = {ColumnID};
            String selection = Column_4 + " = ?"+ " AND " + Column_5 + " = ?";
            String[]  selectionargs = {email,pass};
            Cursor cursor =db.query(TABLE_NAME,columns,selection,selectionargs,null,null,null);
            int cursorcount = cursor.getCount();
            cursor.close();
            db.close();

            if(cursorcount > 0 )
                return  true;
            else
                return false;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return  false;
        }
    }
}
