package br.com.smartroll.model;

public abstract class UserModel {

    public String registrationNumber;
    public String name;
    public String username;
    public String password;

    public UserModel(String registrationNumber, String name, String username, String password) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
