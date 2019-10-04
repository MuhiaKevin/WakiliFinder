package com.wakilifinder.wakilifinder.Model;

//model for client when sending to firebase database

public class Userclient {
    public String email;
    private String imageURL;
    public String userid;
    public String password;
    public String phonenumber;


    public Userclient(String email,String userid, String imageURL, String password, String phonenumber){
        this.email = email;
        this.userid = userid;
        this.imageURL = imageURL;
        this.password = password;
        this.phonenumber = phonenumber;

    }

    public Userclient(){

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }
}
