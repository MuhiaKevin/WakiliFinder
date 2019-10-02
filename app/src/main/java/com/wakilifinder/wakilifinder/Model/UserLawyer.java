package com.wakilifinder.wakilifinder.Model;

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

    public UserLawyer(){

    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
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
}
