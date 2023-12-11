package com.example.chatme;

import android.net.Uri;

public class UserModel {

    public  UserModel()
    {}
    public UserModel(Uri profile_pic, String userName, String mail, String password, String userId, String lastMessage) {
        this.profile_pic = profile_pic;
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
    }
//signup
    public UserModel(String userName, String mail, String password) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
    }

    public UserModel(String userName, String mail, String password, String userId, String lastMessage, Uri profile_pic, String hobbey, String location, String availability, String about) {
        this.userName = userName;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMessage = lastMessage;
        this.profile_pic = profile_pic;
        this.hobbey = hobbey;
        this.location = location;
        this.availability = availability;
        this.about = about;
    }

    public UserModel(String userName, String mail, String password, String userId, String lastMessage, String hobbey, String location, String availability, String about) {

    }

    public Uri getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(Uri profile_pic) {
        this.profile_pic = profile_pic;
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

    String userName,mail,password,userId,lastMessage;
    Uri profile_pic;
    String hobbey,location,availability,about;
    String distance;
    double latitude;

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
