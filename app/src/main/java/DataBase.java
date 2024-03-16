package com.example.databasesql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "base.db";
    public static final String DATABASE_TABLE = "Employes";
    public static final String col_1 = "ID";
    public static final String col_2 = "NOM";
    public static final String col_3 = "EMAIL";
    public static final String col_4 = "PHONE";

    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + DATABASE_TABLE + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, NOM TEXT, EMAIL TEXT, PHONE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(sqLiteDatabase);
    }

    public boolean insertData(String nom, String mail, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, nom);
        contentValues.put(col_3, mail);
        contentValues.put(col_4, phone);
        long result = db.insert(DATABASE_TABLE, null, contentValues);
        return result != -1;
    }

    public Cursor getAllData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor resultat=db.rawQuery("SELECT * FROM " + DATABASE_TABLE, null);
        return resultat;
    }

    public void delete(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(DATABASE_TABLE, col_1 + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public void update(String nom, String mail, String phone, int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(col_2, nom);
        contentValues.put(col_3, mail);
        contentValues.put(col_4, phone);
        db.update(DATABASE_TABLE, contentValues, col_1 + " = ?", new String[]{String.valueOf(id)});
    }
}