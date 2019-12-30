package com.example.firebaselistexample;

public class User {
    private String profile; //Firebase에서 URL 주소로 가져오기 때문에 String 사용
    private String id;
    private int pw;
    private String userName;

    //생성자
    public User(){

    }

    //Getter & Setter
    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPw() {
        return pw;
    }

    public void setPw(int pw) {
        this.pw = pw;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
