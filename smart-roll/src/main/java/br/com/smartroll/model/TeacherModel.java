package br.com.smartroll.model;

import java.util.ArrayList;

public class TeacherModel extends UserModel {

    public TeacherModel(String registrationNumber, String name, String username, String password, ArrayList classes) {
        super(registrationNumber, name, username, password, classes);
    }
}

