package com.wakilifinder.wakilifinder;

//model for client when sending to firebase database

public class Userclient {
    public String email;
    public String password;
    public String phonenumber;


    public Userclient(String email,String password, String phonenumber){
        this.email = email;
        this.password = password;
        this.phonenumber = phonenumber;

    }

}
