package com.wakilifinder.wakilifinder;

public class UserLawyer {

    public String email;
    public String p105number;
    public String practicenumber;
    public String password;


    public UserLawyer(String email,String password, String p105number, String practicenum){
        this.email = email;
        this.password = password;
        this.p105number = p105number;
        this.practicenumber = practicenum;
    }
}
