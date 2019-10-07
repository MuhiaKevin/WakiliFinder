package com.wakilifinder.wakilifinder.Model;

//model for client when sending to firebase database

public class Userclient {
    public String email;
    public String userid;
    public String password;
    public String status;
    public String phonenumber;


    public Userclient(String email,String userid, String password, String phonenumber,String status){
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.status = status;
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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}
