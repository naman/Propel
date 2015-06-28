package com.propel.bluemix.propel;

import android.app.Application;


public class PropelApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        DbSingleton.init(this);/
    }
}
