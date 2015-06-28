package com.propel.bluemix.propel.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.propel.bluemix.propel.Data.Comment;
import com.propel.bluemix.propel.Data.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MananWason on 28062015.
 */
public class DbSingleton {
    private static final String ASCENDING = " ASC";
    private static final String DESCENDING = " DESC";
    private static final String SELECT_ALL = "SELECT * FROM ";
    private static final String WHERE = " WHERE ";
    private static final String EQUAL = " == ";
    private static DbSingleton mInstance;

    SQLiteDatabase mDb;
    private Context mContext;
    private DbHelper mDbHelper;

    private DbSingleton(Context context) {
        mContext = context;
        mDbHelper = new DbHelper(mContext);

    }

    public static void init(Context context) {
        if (mInstance == null) {
            mInstance = new DbSingleton(context);
        }
    }

    public static DbSingleton getInstance() {
        return mInstance;
    }

    private void getReadOnlyDatabase() {
        if ((mDb == null) || (!mDb.isReadOnly())) {
            mDb = mDbHelper.getReadableDatabase();
        }
    }

    public ArrayList<Item> getItemList() {
        getReadOnlyDatabase();

        String sortOrder = DbContract.Posts.ID + ASCENDING;
        Cursor cur = mDb.query(
                DbContract.Posts.TABLE_NAME,
                DbContract.Posts.FULL_PROJECTION,
                null,
                null,
                null,
                null,
                sortOrder
        );

        ArrayList<Item> items = new ArrayList<>();
        Item current;

        cur.moveToFirst();
        Log.d("DB",cur.getPosition()+"");

        while (!cur.isAfterLast()) {
            current = new Item(
                    cur.getString(cur.getColumnIndex(DbContract.Posts.TITLE)),
                    cur.getString(cur.getColumnIndex(DbContract.Posts.DESCRIPTION)),
                    cur.getString(cur.getColumnIndex(DbContract.Posts.DATE_TIME))
            );
            items.add(current);
            cur.moveToNext();
            Log.d("DB",current.getName());
        }
        cur.close();
        return items;
    }


    public ArrayList<Comment> getCommentByPostId(int id) {
        getReadOnlyDatabase();
        String sortOrder = DbContract.Comments.ID + ASCENDING;
        String selection = DbContract.Comments.POST_ID +EQUAL + id;
        Cursor cur = mDb.query(
                DbContract.Comments.TABLE_NAME,
                DbContract.Comments.FULL_PROJECTION,
                selection,
                null,
                null,
                null,
                sortOrder
        );
            ArrayList<Comment> items = new ArrayList<>();
        Comment current;

        cur.moveToFirst();
        while (!cur.isAfterLast()) {
            current = new Comment(cur.getString(cur.getColumnIndex(DbContract.Comments.COMMENT_TEXT)),
                    cur.getInt(cur.getColumnIndex(DbContract.Comments.LIKES)),
                    cur.getString(cur.getColumnIndex(DbContract.Comments.DATE_TIME)));
            items.add(current);
                cur.moveToNext();
        }
        cur.close();
        return items;
    }

    public void insertQueries(String query) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        db.execSQL(query);
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    public void clearDatabase(String table) {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.beginTransaction();
        try {

            db.delete(table, null, null);

            db.setTransactionSuccessful();

        } finally {
            db.endTransaction();
        }
    }
}