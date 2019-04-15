package com.example.koffer;

public class User {
    String email;
    String password;
    String password2;
    String tel;

    public User(String email, String password, String password2, String tel) {
        this.email = email;
        this.password = password;
        this.password2 = password2;
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPassword2() {
        return password2;
    }

    public String getTel() {
        return tel;
    }
}
