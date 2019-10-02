package com.wakilifinder.wakilifinder;

//model for client when sending to firebase database

public class Userclient {
    public String email;
    public String userid;
    public String password;
    public String phonenumber;


    public Userclient(String email,String userid, String password, String phonenumber){
        this.email = email;
        this.userid = userid;
        this.password = password;
        this.phonenumber = phonenumber;

    }

}
