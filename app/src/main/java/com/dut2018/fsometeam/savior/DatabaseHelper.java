package com.dut2018.fsometeam.savior;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "AOCustomer.db";
    private static final String TABLE_NAME = "AOCustomer";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_PHONE = "phone";
    private static final String COLUMN_PASS = "pass";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = String.format("create table AOCustomer (id interger primary key not null auto_increment , name text not phone , null text not null , pass text not null);");

    public DatabaseHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;

    }
    public void insertAOCustomer(AccoutOfCustomer a)
    {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        String query = "select * from AOCustomer";
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, a.getName());
        values.put(COLUMN_PHONE, a.getPhonenumber());
        values.put(COLUMN_PASS, a.getPass());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public String searchPass(String phone)
    {
        db = this.getReadableDatabase();
        String query = "select phonenumber, pass from "+TABLE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        String a, p;
        p = "not found";
        if (cursor.moveToFirst())
        {
            do{
                a = cursor.getString(0);


                if (a.equals(phone))
                {
                    p = cursor.getString(1);
                    break;
                }
            }
            while(cursor.moveToNext());

        }
        db.close();
        return p;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);

    }
}
