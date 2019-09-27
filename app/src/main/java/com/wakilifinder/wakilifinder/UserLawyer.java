package com.wakilifinder.wakilifinder;

// model for lawyer when sending to firebase database

public class UserLawyer {

    public String profileimage;
    public String email;
    public String p105number;
    public String practicenumber;
    public String password;


    public UserLawyer(String profileimage, String email,String password, String p105number, String practicenum){
        this.profileimage = profileimage;
        this.email = email;
        this.password = password;
        this.p105number = p105number;
        this.practicenumber = practicenum;
    }
}
