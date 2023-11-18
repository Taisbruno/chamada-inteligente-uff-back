package br.com.smartroll.model;

public class StudentModel extends UserModel {

    public double frequency;

    public boolean failed;

    public StudentModel(String registrationNumber, String name, String username, String password) {
        super(registrationNumber, name, username, password);
    }
}

