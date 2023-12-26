package com.example.chatme;

import android.net.Uri;

public class UserModel {


    String userName,mail,password,userId,lastMessage;
    String profilepic;
    String hobbey,location,availability,about;
    String distance;
    double latitude;
    long lastmessageTime;
    String lastmessageTo;

    public String getLastmessageTo() {
        return lastmessageTo;
    }

    public void setLastmessageTo(String lastmessageTo) {
        this.lastmessageTo = lastmessageTo;
    }

    public long getLastmessageTime() {
        return lastmessageTime;
    }

    public void setLastmessageTime(long lastmessageTime) {
        this.lastmessageTime = lastmessageTime;
    }

    public  UserModel()
    {}

//signup
    public UserModel(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }



    public String getProfilepic() {
        return profilepic;
    }

    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserId(String key) {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }



    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    double longitude;

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getHobbey() {
        return hobbey;
    }

    public void setHobbey(String hobbey) {
        this.hobbey = hobbey;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

}
