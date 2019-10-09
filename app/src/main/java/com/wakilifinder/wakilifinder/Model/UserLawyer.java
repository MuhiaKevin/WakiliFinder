package com.wakilifinder.wakilifinder.Model;

// model for lawyer when sending to firebase database

public class UserLawyer {
    public String userid;
    public String username;
    public String imageurl;
    public String email;
    public String p105number;
    public String practicenumber;
    public String password;
    public String status;


    public UserLawyer(String userid,String imageurl, String email,String password, String p105number, String practicenum,  String status, String username){
        this.imageurl = imageurl;
        this.userid = userid;
        this.email = email;
        this.username = username;
        this.password = password;
        this.p105number = p105number;
        this.status = status;
        this.practicenumber = practicenum;
    }

    public UserLawyer(){

    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String profileimage) {
        this.imageurl = profileimage;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getP105number() {
        return p105number;
    }

    public void setP105number(String p105number) {
        this.p105number = p105number;
    }

    public String getPracticenumber() {
        return practicenumber;
    }

    public void setPracticenumber(String practicenumber) {
        this.practicenumber = practicenumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    // alt + insert


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
