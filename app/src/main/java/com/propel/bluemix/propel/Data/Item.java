package com.propel.bluemix.propel.Data;

import android.database.DatabaseUtils;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;
import com.propel.bluemix.propel.Database.DbContract;

import java.io.Serializable;

@IBMDataObjectSpecialization("Item")
public class Item extends IBMDataObject implements Serializable {
    String name;
    String description;
    String post_date;
    Comment[] comments = null;

    public Item(String name, String description, String post_date) {
        this.post_date = post_date;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getDescription() {
        return description;

    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String generateSql(int id) {
        String query_normal = "INSERT INTO %s VALUES ('%d',%s, %s, %s);";
        String query = String.format(
                query_normal,
                DbContract.Posts.TABLE_NAME,
                id,
                DatabaseUtils.sqlEscapeString(name),
                DatabaseUtils.sqlEscapeString(description),
                DatabaseUtils.sqlEscapeString(post_date));
        return query;

    }
}