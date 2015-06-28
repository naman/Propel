package com.propel.bluemix.propel.Data;

public class Comment {
    String comment;
    int likes;
    String DateofPublish;


    public Comment(String comment, int likes, String DateofPublish) {

        this.comment = comment;
        this.likes = likes;
        this.DateofPublish = DateofPublish;
    }

    public String getDateofPublish() {
        return DateofPublish;
    }

    public void setDateofPublish(String dateofPublish) {
        DateofPublish = dateofPublish;
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
