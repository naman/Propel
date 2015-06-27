package com.propel.bluemix.propel;

import android.app.Application;

import com.propel.bluemix.propel.Database.DbSingleton;

/**
 * Created by MananWason on 28-06-2015.
 */
public class PropelApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        DbSingleton.init(this);
    }
}
