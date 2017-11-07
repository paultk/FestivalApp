package com.example.paulthomaskorsvold.festivalappclean.utils.database_utils;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.paulthomaskorsvold.festivalappclean.models.ChecklistItem;


/**
 * Created by paulthomaskorsvold on 10/12/17.
 */


public class ChecklistProvider extends ContentProvider {

    private static final String M_TAG = "ChecklistProvider";

    public enum DbMethod{
        DELETE, INSERT, UPDATE

    }

    // database
    private SQLiteDatabase mDb;
    private DBHelper mDBHelper;

    // providers
    private final static String AUTHORITY = "com.example.paulthomaskorsvold.festivalappclean";
    private final static String CHECKLIST_ITEM_TABLE = ChecklistItem.TABLE;
    public static final Uri CONTENT_URI = Uri.parse("content://" +
            AUTHORITY + "/" + CHECKLIST_ITEM_TABLE);

    public static final int CHECKLIST_ITEMS = 1;
    public static final int CHECKLIST_ITEMS_ID = 2;
    public static final String CHECKLIST_ITEM_ID = "_id";

    // static variable for the urimatcher that gets constructed
    private static final UriMatcher S_URI_MATCHER = buildUriMatcher();

    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, CHECKLIST_ITEM_TABLE, CHECKLIST_ITEMS);
        uriMatcher.addURI(AUTHORITY, CHECKLIST_ITEM_TABLE + "/#", CHECKLIST_ITEMS_ID);
        return uriMatcher;

    }


    @Override
    public boolean onCreate() {
        mDBHelper = new DBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor returnCursor;
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(CHECKLIST_ITEM_TABLE);
        int uriType = S_URI_MATCHER.match(uri);

        switch (uriType) {

            case CHECKLIST_ITEMS:
                break;
            case CHECKLIST_ITEMS_ID:
                queryBuilder.appendWhere(CHECKLIST_ITEMS_ID + "="
                        + uri.getLastPathSegment()
                );
                break;
            default:
                throw new UnsupportedOperationException("unknown uri" + uri);
        }

        returnCursor = queryBuilder.query(mDBHelper.getReadableDatabase(),
                projection, selection, selectionArgs, null, null, sortOrder);
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        long id = dbMethod(DbMethod.INSERT, uri, contentValues);
        getContext().getContentResolver().notifyChange(uri, null);
        return uri.parse(CHECKLIST_ITEM_TABLE + "/" + id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int uriType = S_URI_MATCHER.match(uri);
        int rowsDeleted = 0;

        mDb = mDBHelper.getWritableDatabase();

        switch (uriType) {
            case CHECKLIST_ITEMS:
                Log.d("", "CHECKLIST_ITEMS: " + CHECKLIST_ITEMS + "\nuri" + uri);
                rowsDeleted = mDb.delete(CHECKLIST_ITEM_TABLE, selection, selectionArgs);
                break;
            case CHECKLIST_ITEMS_ID:
                Log.d("", "CHECKLIST_ITEMS_ID: " + CHECKLIST_ITEMS_ID + "\nuri" + uri);

                String id = uri.getLastPathSegment();
                rowsDeleted = mDb.delete(CHECKLIST_ITEM_TABLE, ChecklistItem.KEY_ID + " = " + id, selectionArgs);
                Log.d(M_TAG, "id: " + id);
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        int uriType = S_URI_MATCHER.match(uri);
        int count = 0;
        mDb = mDBHelper.getWritableDatabase();

        switch (uriType) {
            case CHECKLIST_ITEMS:
                count = mDb.update(CHECKLIST_ITEM_TABLE, contentValues, selection, selectionArgs);
                break;
            case CHECKLIST_ITEMS_ID:
                count = mDb.update(CHECKLIST_ITEM_TABLE, contentValues, ChecklistItem.KEY_ID + " = " + uri.getPathSegments().get(1)
                        + (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs
                );
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);

        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    private long dbMethod(DbMethod dbMethod, @NonNull Uri uri, @Nullable ContentValues contentValues) {
        int uriType = S_URI_MATCHER.match(uri);
        mDb = mDBHelper.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case CHECKLIST_ITEMS:
                id = mDb.insert(CHECKLIST_ITEM_TABLE, null, contentValues);
                break;
            default:
                throw new IllegalArgumentException("unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return id;
    }
}
