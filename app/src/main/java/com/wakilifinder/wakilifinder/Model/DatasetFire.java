package com.wakilifinder.wakilifinder.Model;

import android.widget.ImageView;

public class DatasetFire {
    public String email,userid,imageurl, p105number,password, practicenumber ;

    public DatasetFire(String email,String userid, String imageurl, String p105number, String password, String practicenumber) {
        this.email = email;
        this.userid = userid;
        this.imageurl = imageurl;
        this.p105number = p105number;
        this.password = password;
        this.practicenumber = practicenumber;
    }

    public String getEmail() {
        return email;
    }

    public DatasetFire(){

    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getP105number() {
        return "P.105/"+ p105number;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setP105number(String p105number) {
        this.p105number = p105number;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPracticenumber() {
        return "Practice Number  "+ practicenumber;
    }

    public void setPracticenumber(String practicenumber) {
        this.practicenumber = practicenumber;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getImageurl() {
        return imageurl;
    }


}
