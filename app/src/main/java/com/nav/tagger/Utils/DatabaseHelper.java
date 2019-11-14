package com.nav.tagger.Utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nav.tagger.Model.AddTagList;
import com.nav.tagger.Model.TagImageList;

import java.util.ArrayList;

/**
 * Created by navitgupta on 09/06/18.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 2;

    // Database Name
    private static final String DATABASE_NAME = "tagger_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(Constants.CREATE_TABLE);
        db.execSQL(Constants.CREATE_TABLE_ADD_TAG);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME_ADD_TAG);

        // Create tables again
        onCreate(db);
    }

    public long insertADDTAG(String note) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Constants.COLUMN_TAG_ADD, note);
        values.put(Constants.COLUMN_TAG_HIDE, "unhide");

        // insert row
        long id = db.insert(Constants.TABLE_NAME_ADD_TAG, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public long insertTAGIMAGE(String tag,String tag_image) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(Constants.COLUMN_TAG, tag);
        values.put(Constants.COLUMN_IMAGE, tag_image);

        // insert row
        long id = db.insert(Constants.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public ArrayList<AddTagList> getAllTags() {
        ArrayList<AddTagList> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constants.TABLE_NAME_ADD_TAG + " ORDER BY " +
                Constants.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AddTagList tagList = new AddTagList();
                tagList.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID_TAG)));
                tagList.setTag(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TAG_ADD)));
                tagList.setHide(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TAG_HIDE)));
                tagList.setTimestamp(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TIMESTAMP)));

                notes.add(tagList);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }

    public ArrayList<TagImageList> getAllTagsImages() {
        ArrayList<TagImageList> notes = new ArrayList<>();

        // Select All Query
        String selectQuery = "SELECT  * FROM " + Constants.TABLE_NAME + " ORDER BY " +
                Constants.COLUMN_TIMESTAMP + " DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                TagImageList tagList = new TagImageList();
                tagList.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID)));
                tagList.setTag(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TAG)));
                tagList.setTag_image(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_IMAGE)));
                tagList.setTimestamp(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TIMESTAMP)));

                notes.add(tagList);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        // return notes list
        return notes;
    }


    public ArrayList<TagImageList>  getAllTagsImageByTag(String tag) {
        ArrayList<TagImageList> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME,
                new String[]{Constants.COLUMN_ID, Constants.COLUMN_TAG, Constants.COLUMN_IMAGE,Constants.COLUMN_TIMESTAMP},
                Constants.COLUMN_TAG + "=?",
                new String[]{String.valueOf(tag)}, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                TagImageList tagList = new TagImageList();
                tagList.setId(cursor.getInt(cursor.getColumnIndex(Constants.COLUMN_ID)));
                tagList.setTag(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TAG)));
                tagList.setTag_image(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_IMAGE)));
                tagList.setTimestamp(cursor.getString(cursor.getColumnIndex(Constants.COLUMN_TIMESTAMP)));

                notes.add(tagList);
            } while (cursor.moveToNext());
        }

        // close db connection
        db.close();

        return notes;
    }

    public int updateTAG(AddTagList note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TAG_ADD, note.getTag());

        // updating row
        return db.update(Constants.TABLE_NAME_ADD_TAG, values, Constants.COLUMN_ID_TAG + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public int updateTAGHIDE(AddTagList note) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Constants.COLUMN_TAG_HIDE, note.getHide());

        // updating row
        return db.update(Constants.TABLE_NAME_ADD_TAG, values, Constants.COLUMN_ID_TAG + " = ?",
                new String[]{String.valueOf(note.getId())});
    }

    public void deleteNote(AddTagList note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME_ADD_TAG, Constants.COLUMN_ID_TAG + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }
    public void deleteTAGIMAGENOTE(TagImageList note) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME, Constants.COLUMN_ID + " = ?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }


}
