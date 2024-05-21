package com.mango.artsparkxml;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "DatabaseHandler";
    private static final String TBL_ENTRY = "tbl_Entry";
    private static final String ENTRYID = "entryId";
    private static final String ENTRYTITLE = "entryTitle";
    private static final String CONTENT = "content";
    private static final String DATE = "datetbl";
    boolean firstCreated;
    SQLiteDatabase db;

    public DatabaseHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        firstCreated = true;
        String CREATE_TABLE_ENTRY = "CREATE TABLE " + TBL_ENTRY + "("
                + ENTRYID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ENTRYTITLE + " TEXT,"
                + CONTENT + " TEXT,"
                + DATE + " TEXT)";
        db.execSQL(CREATE_TABLE_ENTRY);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TBL_ENTRY);
        onCreate(db);
    }
}
