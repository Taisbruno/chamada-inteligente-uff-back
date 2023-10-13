package br.com.smartroll.model;

import java.util.ArrayList;
import java.util.List;

public class ClassModel {
    public String classCode;
    public String disciplineCode;
    public String discipline;
    public String teacher;
    public String semester;
    public int total;

    public ClassModel(String classCode, String disciplineCode, String discipline, String teacher, String semester, int total) {
        this.disciplineCode = disciplineCode;
        this.classCode = classCode;
        this.discipline = discipline;
        this.teacher = teacher;
        this.semester = semester;
        this.total = total;
    }
}
