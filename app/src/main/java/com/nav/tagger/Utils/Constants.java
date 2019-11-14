package com.nav.tagger.Utils;

/**
 * Created by navitgupta on 09/06/18.
 */

public class Constants {
    public static final String TABLE_NAME = "tagger";
    public static final String TABLE_NAME_ADD_TAG = "tagger_tag_add";


    public static final String COLUMN_ID_TAG = "id_tag";
    public static final String COLUMN_TAG_ADD = "tag_add";
    public static final String COLUMN_TAG_HIDE = "tag_hide";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TAG = "tag";
    public static final String COLUMN_IMAGE = "tag_image";
    public static final String COLUMN_TIMESTAMP = "timestamp";



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TAG + " TEXT,"
                    + COLUMN_IMAGE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public static final String CREATE_TABLE_ADD_TAG =
            "CREATE TABLE " + TABLE_NAME_ADD_TAG + "("
                    + COLUMN_ID_TAG + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TAG_ADD + " TEXT,"
                    + COLUMN_TAG_HIDE + " TEXT,"
                    + COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";


}
