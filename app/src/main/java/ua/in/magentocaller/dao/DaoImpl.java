package ua.in.magentocaller.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ua.obolon.ponovoy.inerfaces.Call;
import ua.in.magentocaller.interfaces.DAO;
import ua.obolon.ponovoy.impl.Call_Impl;

/**
 * Created by Alexander on 18.09.2016.
 */
public class DaoImpl  extends SQLiteOpenHelper implements DAO {

    public DaoImpl(Context context) {
        super(context, "MissedCallsDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table calls("
                + "id integer primary key autoincrement,"
                + "number text,"
                + "time datetime" + ");");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    @Override
    public boolean addMissedCall(String number) {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("number", number);
        contentValues.put("time", new Date().getTime());
        dataBase.insert("calls", null, contentValues);
        dataBase.close();
        return true;
    }


    @Override
    public List<Call> getAllMissed() {
        List<Call> list = new ArrayList<>();
        SQLiteDatabase dataBase = this.getWritableDatabase();
        Cursor cursor = dataBase.query("calls", null, null, null, null, null, null);
        if (cursor.moveToFirst()){

            int numberColIndex = cursor.getColumnIndex("number");
            int timeColIndex = cursor.getColumnIndex("time");
            do {
                Call call = new Call_Impl();
                call.setNumber(cursor.getString(numberColIndex));
                call.setDate(cursor.getLong(timeColIndex));
                list.add(call);
            }while (cursor.moveToNext());
            cursor.close();
            dataBase.close();
            return list;
        }else {
            return null;
        }
    }

    @Override
    public int clearMissed() {
        SQLiteDatabase dataBase = this.getWritableDatabase();
        return dataBase.delete("calls", null, null);
    }


}