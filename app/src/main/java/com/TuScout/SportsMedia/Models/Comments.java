package com.TuScout.SportsMedia.Models;

public class Comments {

    private String userName;
    private String userProfileImg;
    private String userComment;

    public Comments () {}

    public Comments (String userName, String userProfileImg, String userComment) {
        this.userName = userName;
        this.userProfileImg = userProfileImg;
        this.userComment = userComment;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserProfileImg() {
        return userProfileImg;
    }

    public void setUserProfileImg(String userProfileImg) {
        this.userProfileImg = userProfileImg;
    }

    public String getUserComment() {
        return userComment;
    }

    public void setUserComment(String userComment) {
        this.userComment = userComment;
    }
}
