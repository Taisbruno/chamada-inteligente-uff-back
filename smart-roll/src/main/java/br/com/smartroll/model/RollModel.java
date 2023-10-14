package br.com.smartroll.model;

import java.util.ArrayList;
import java.util.List;

public class RollModel {
    public String id;
    public String initialDate;
    public String finalDate;
    public String longitude;
    public String latitude;
    public List<StudentModel> students = new ArrayList<>();
}
