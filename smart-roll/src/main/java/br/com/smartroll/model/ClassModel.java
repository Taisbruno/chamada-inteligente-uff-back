package br.com.smartroll.model;

import java.util.ArrayList;
import java.util.List;

public class ClassModel {
    public String code;
    public String discipline;
    public String teacher;
    public String semester;
    public int total;
    public List<StudentModel> students = new ArrayList<>();

    public ClassModel(String code, String discipline, String teacher, String semester, int total, List students) {
        this.code = code;
        this.discipline = discipline;
        this.teacher = teacher;
        this.semester = semester;
        this.total = total;
        this.students.addAll(students);
    }
}
