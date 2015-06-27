package com.propel.bluemix.propel.Database;

import android.provider.BaseColumns;

/**
 * Created by MananWason on 27-06-2015.
 */
public class DbContract {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "OpenEvent.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String INT_TYPE = " INTEGER";
    private static final String REAL_TYPE = " REAL";
    private static final String COMMA_SEP = ",";
    private static final String PRIMARY_KEY = " PRIMARY KEY";

    public DbContract() {
        //Empty constructor to prevent object creation.
    }

    public static abstract class Posts implements BaseColumns {
        public static final String TABLE_NAME = "posts";
        public static final String ID = "id";
        public static final String TITLE = "title";
        public static final String DESCRIPTION = "description";
        public static final String DATE_TIME = "date_time";

        public static final String[] FULL_PROJECTION = {
                ID,
                TITLE,
                DESCRIPTION,
                DATE_TIME
        };
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + ID + INT_TYPE + PRIMARY_KEY + COMMA_SEP
                        + TITLE + TEXT_TYPE + COMMA_SEP
                        + DESCRIPTION + TEXT_TYPE + COMMA_SEP
                        + DATE_TIME + TEXT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;


    }

    public static abstract class Comments implements BaseColumns {
        public static final String TABLE_NAME = "comments";
        public static final String ID = "id";
        public static final String POST_ID = "id";
        public static final String COMMENT_TEXT = "comment_text";
        public static final String LIKES = "likes";
        public static final String DATE_TIME = "date_time";
        public static final String[] FULL_PROJECTION = {
                ID,
                POST_ID,
                COMMENT_TEXT,
                LIKES,
                DATE_TIME
        };
        public static final String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME
                        + " ("
                        + ID + PRIMARY_KEY + COMMA_SEP
                        + POST_ID + COMMA_SEP
                        + TEXT_TYPE + COMMA_SEP
                        + COMMENT_TEXT + TEXT_TYPE + COMMA_SEP
                        + LIKES + INT_TYPE + COMMENT_TEXT
                        + DATE_TIME + TEXT_TYPE
                        + " );";
        public static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    }

}
