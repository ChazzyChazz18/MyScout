package com.TuScout.SportsMedia.Models;

public class Messages {

    private String transmitterUserProfileImage;
    private String transmitterUserName;
    private String transmitterUserMessage;

    public Messages () {}

    public Messages (String transmitterUserName, String transmitterUserProfileImage, String transmitterUserMessage) {
        this.transmitterUserProfileImage = transmitterUserProfileImage;
        this.transmitterUserName = transmitterUserName;
        this.transmitterUserMessage = transmitterUserMessage;
    }

    public String getTransmitterUserProfileImage() {
        return transmitterUserProfileImage;
    }

    public void setTransmitterUserProfileImage(String transmitterUserProfileImage) {
        this.transmitterUserProfileImage = transmitterUserProfileImage;
    }

    public String getTransmitterUserName() {
        return transmitterUserName;
    }

    public void setTransmitterUserName(String transmitterUserName) {
        this.transmitterUserName = transmitterUserName;
    }

    public String getTransmitterUserMessage() {
        return transmitterUserMessage;
    }

    public void setTransmitterUserMessage(String transmitterUserMessage) {
        this.transmitterUserMessage = transmitterUserMessage;
    }
}
