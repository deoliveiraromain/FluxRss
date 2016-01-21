package fr.deoliveira.fluxrss.app.base;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;

import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Romain on 30/09/2015.
 */
public class RSSContentProvider extends ContentProvider {

    // database
    private DatabaseHelper database;

    // used for the UriMacher
    private static final int RSSS = 10;
    private static final int RSS_ID = 20;

    private static final String AUTHORITY = "fr.deoliveira.fluxrss.app.rsscontentprovider";

    private static final String BASE_PATH = "rss";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/rsss";
    public static final String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/rss";

    private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sURIMatcher.addURI(AUTHORITY, BASE_PATH, RSSS);
        sURIMatcher.addURI(AUTHORITY, BASE_PATH + "/#", RSS_ID);
    }

    @Override
    public boolean onCreate() {
        database = new DatabaseHelper(getContext());
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        // Uisng SQLiteQueryBuilder instead of query() method
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // check if the caller has requested a column which does not exists
        checkColumns(projection);
        // Set the table
        queryBuilder.setTables(RSSTable.TABLE_RSS);
        int uriType = sURIMatcher.match(uri);
        switch (uriType) {
            case RSSS:
                break;
            case RSS_ID:
                // adding the ID to the original query
                queryBuilder.appendWhere(RSSTable.COLUMN_KEY + "="
                        + uri.getLastPathSegment());
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        SQLiteDatabase db = database.getWritableDatabase();
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);
        // make sure that potential listeners are getting notified
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        long id = 0;
        switch (uriType) {
            case RSSS:
                // if(this.getCount(values.getAsInteger(RSSTable.COLUMN_ID)) == 0){
                id = sqlDB.insert(RSSTable.TABLE_RSS, null, values);
                //}
                // else{
                //    sqlDB.update(RSSTable.TABLE_RSS,values, RSSTable.COLUMN_KEY + " = ? ",new String[]{String.valueOf(values.getAsInteger(RSSTable.COLUMN_KEY))});
                // }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(BASE_PATH + "/" + id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsDeleted = 0;
        switch (uriType) {
            case RSSS:
                rowsDeleted = sqlDB.delete(RSSTable.TABLE_RSS, selection,
                        selectionArgs);
                break;
            case RSS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsDeleted = sqlDB.delete(RSSTable.TABLE_RSS,
                            RSSTable.COLUMN_KEY + "=" + id,
                            null);
                } else {
                    rowsDeleted = sqlDB.delete(RSSTable.TABLE_RSS,
                            RSSTable.COLUMN_KEY + "=" + id
                                    + " and " + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {

        int uriType = sURIMatcher.match(uri);
        SQLiteDatabase sqlDB = database.getWritableDatabase();
        int rowsUpdated = 0;
        switch (uriType) {
            case RSSS:
                rowsUpdated = sqlDB.update(RSSTable.TABLE_RSS,
                        values,
                        selection,
                        selectionArgs);
                break;
            case RSS_ID:
                String id = uri.getLastPathSegment();
                if (TextUtils.isEmpty(selection)) {
                    rowsUpdated = sqlDB.update(RSSTable.TABLE_RSS,
                            values,
                            RSSTable.COLUMN_KEY + "=" + id,
                            null);
                } else {
                    rowsUpdated = sqlDB.update(RSSTable.TABLE_RSS,
                            values,
                            RSSTable.COLUMN_KEY + "=" + id
                                    + " and "
                                    + selection,
                            selectionArgs);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown URI: " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return rowsUpdated;
    }

    private void checkColumns(String[] projection) {
        //"COUNT(*)",
        String[] available = RSSTable.getProjection();
        if (projection != null) {
            HashSet<String> requestedColumns = new HashSet<String>(Arrays.asList(projection));
            HashSet<String> availableColumns = new HashSet<String>(Arrays.asList(available));
            // check if all columns which are requested are available
            if (!availableColumns.containsAll(requestedColumns)) {
                throw new IllegalArgumentException("Unknown columns in projection");
            }
        }
    }

    public int getCount(int id) {
        Cursor c = this.query(CONTENT_URI, new String[]{"COUNT(*)"}, RSSTable.COLUMN_KEY + "= ?",
                new String[]{String.valueOf(id)}, null);
        if (c.moveToFirst()) {
            return c.getInt(0);
        }
        return 0;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        return super.call(method, arg, extras);
    }

}
