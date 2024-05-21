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
    void addEntry(tbl_Entry entry){
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ENTRYTITLE, entry.getEntryTitle());
        values.put(CONTENT, entry.getContent());
        values.put(DATE, entry.getDate());
        db.insert(TBL_ENTRY, null, values);
    }
    tbl_Entry getEntry(int id){
        if (db == null)
            db = getWritableDatabase();
        Cursor cursor = db.query(TBL_ENTRY,
                new String[]{ENTRYID,
                        ENTRYTITLE,
                        CONTENT,
                        DATE},
                ENTRYID + "=?",
                new String[]{String.valueOf(id)}, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        tbl_Entry entry = new tbl_Entry();
        entry.setEntryId(cursor.getInt(0));
        entry.setEntryTitle(cursor.getString(1));
        entry.setContent(cursor.getString(2));
        entry.setDate(cursor.getLong(3));
        return entry;
    }

    public ArrayList<tbl_Entry> getAllEntry() {
        ArrayList<tbl_Entry> entryList = new ArrayList<tbl_Entry>();
        String selectQuery = " SELECT * FROM " + TBL_ENTRY;
        if (db == null)
            db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                tbl_Entry entry = new tbl_Entry();
                entry.setEntryId(Integer.parseInt(cursor.getString(0)));
                entry.setEntryTitle(cursor.getString(1));
                entry.setContent(cursor.getString(2));
                entry.setDate(cursor.getLong(3));
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        return entryList;
    }
    public List<tbl_Entry> findEntry(String where, String orderBy) {
        List<tbl_Entry> entryList = new ArrayList<tbl_Entry>();
        String selectQuery = " SELECT * FROM " + TBL_ENTRY +
                " WHERE " + where +
                " ORDER BY " + orderBy;
        if (db == null)
            db = getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                tbl_Entry entry = new tbl_Entry();
                entry.setEntryId(Integer.parseInt(cursor.getString(0)));
                entry.setEntryTitle(cursor.getString(1));
                entry.setContent(cursor.getString(2));
                entry.setDate(cursor.getLong(3));
                // Adding contact to list
                entryList.add(entry);
            } while (cursor.moveToNext());
        }
        return entryList;
    }

    public int updateEntry(tbl_Entry entry) {
        if (db == null)
            db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ENTRYTITLE, entry.getEntryTitle());
        values.put(CONTENT, entry.getContent());
        values.put(DATE, entry.getDate());
        // updating row
        return db.update(TBL_ENTRY, values, ENTRYID + " = ?",
                new String[]{String.valueOf(entry.getEntryId())});
    }
    public void deleteEntry(tbl_Entry entry) {
        if (db == null)
            db = getWritableDatabase();
        db.delete(TBL_ENTRY, ENTRYID + " = ?",
                new String[]{String.valueOf(entry.getEntryId())});
    }
}
