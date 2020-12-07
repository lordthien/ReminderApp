package com.example.ngocthien.remindertj;

public class Password {
    String password;
    String repassword;

    public Password() {
    }
    public Password(String password, String repassword) {
        this.password = password;
        this.repassword = repassword;
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
