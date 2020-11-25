package com.example.ngocthien.remindertj.Login;

public class UserInfo {
    String phone, password, repassword;

    public UserInfo() {
    }
    public UserInfo(String phone, String password, String repassword) {
        this.phone = phone;
        this.password = password;
        this.repassword = repassword;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRepassword() {
        return repassword;
    }
    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
