package com.propel.bluemix.propel.Data;

import java.util.Date;

/**
 * Created by MananWason on 27-06-2015.
 */
public class Comment {
    Item reference;
    String comment;
    int likes;
    Date ofPublish;

    public Comment(Item reference, String comment, int likes, Date ofPublish) {
        this.reference = reference;
        this.comment = comment;
        this.likes = likes;
        this.ofPublish = ofPublish;
    }

    public Item getReference() {
        return reference;
    }

    public void setReference(Item reference) {
        this.reference = reference;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public Date getOfPublish() {
        return ofPublish;
    }

    public void setOfPublish(Date ofPublish) {
        this.ofPublish = ofPublish;
    }
}
