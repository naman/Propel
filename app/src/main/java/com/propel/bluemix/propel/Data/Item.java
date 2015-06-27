package com.propel.bluemix.propel.Data;

import com.ibm.mobile.services.data.IBMDataObject;
import com.ibm.mobile.services.data.IBMDataObjectSpecialization;

import java.io.Serializable;
import java.util.Date;

@IBMDataObjectSpecialization("Item")
public class Item extends IBMDataObject implements Serializable {
    String name;
    String description;
    String post_date;

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
}