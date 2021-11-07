package com.example.cookmaster.model;

public class User {
    public User(int id, String name, String surname, String mail, String password_hash, String salt, boolean license){
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.mail = mail;
        this.password_hash = password_hash;
        this.salt = salt;
        this.license = license;
    }

    public int id;
    public String name;
    public String surname;
    public String mail;
    public String password_hash;
    public String salt;
    public boolean license;
}
