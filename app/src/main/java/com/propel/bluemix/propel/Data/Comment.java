package com.propel.bluemix.propel.Data;

import java.util.Date;

public class Comment {
    Item reference;
    String comment;
    int likes;
    String ofPublish;


    public Comment(String comment, int likes, String ofPublish) {
        this.reference = reference;
        this.comment = comment;
        this.likes = likes;
        this.ofPublish = ofPublish;
    }

    public String getOfPublish() {
        return ofPublish;
    }

    public void setOfPublish(String ofPublish) {
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


}
