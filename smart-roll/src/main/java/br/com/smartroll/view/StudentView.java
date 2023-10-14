package br.com.smartroll.view;

import br.com.smartroll.model.StudentModel;
import com.google.gson.annotations.SerializedName;

public class StudentView {
    @SerializedName("registration")
    public String registration;

    @SerializedName("name")
    public String name;

    public StudentView(StudentModel studentModel) {
        this.registration = studentModel.registrationNumber;
        this.name = studentModel.name;
    }

    public StudentView(String registrationNumber, String name) {
        this.registration = registrationNumber;
        this.name = name;
    }
}
