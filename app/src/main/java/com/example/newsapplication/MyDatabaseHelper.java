package com.example.newsapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static final String CREATE_HISTORY = "Create table History("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "url text,"
            + "image text,"
            + "video text,"
            + "publisher text,"
            + "time text,"
            + "content text)";


    private static final String CREATE_COLLECTION = "Create table Collection("
            + "id integer primary key autoincrement,"
            + "title text,"
            + "url text,"
            + "image text,"
            + "video text,"
            + "publisher text,"
            + "time text,"
            + "content text)";

    private Context mContext;
    public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                            int version)
    {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_COLLECTION);
        sqLiteDatabase.execSQL(CREATE_HISTORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists History");
        sqLiteDatabase.execSQL("drop table if exists Collection");
        onCreate(sqLiteDatabase);
    }
}
