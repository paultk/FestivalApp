package com.example.paulthomaskorsvold.festivalappclean.utils.database_utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.paulthomaskorsvold.festivalappclean.models.ChecklistItem;


/**
 * Created by paulthomaskorsvold on 10/12/17.
 */



public class DBHelper extends SQLiteOpenHelper {
    // Version number to upgrade database version
    // each time if you Add, Edit table, you need to change the
    // version number.

    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Checklist.db";
    public static final String CHECKLIST_NAME = "checklist_item";

    private final Context context;

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here

        String CREATE_TABLE_GAME = "CREATE TABLE " + ChecklistItem.TABLE + '('
                + ChecklistItem.KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + ChecklistItem.KEY_TITLE + " TEXT, "
                + ChecklistItem.KEY_DATE + " TEXT, "
                + ChecklistItem.KEY_STATUS + " TEXT, "
                + ChecklistItem.KEY_DESCRIPTION + " TEXT )";

        db.execSQL(CREATE_TABLE_GAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + ChecklistItem.TABLE);

        // Create tables again
        onCreate(db);
    }
}

