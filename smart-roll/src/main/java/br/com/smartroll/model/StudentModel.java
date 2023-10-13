package br.com.smartroll.model;

import java.util.ArrayList;

public class StudentModel extends UserModel {

    public StudentModel(String registrationNumber, String name, String username, String password, ArrayList classes) {
        super(registrationNumber, name, username, password, classes);
    }
}

