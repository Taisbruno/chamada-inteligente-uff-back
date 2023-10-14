package br.com.smartroll.view;

import br.com.smartroll.model.ClassModel;
import br.com.smartroll.model.StudentModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ClassesViews {

    @SerializedName("classrooms")
    private List<ClassView> classes = new ArrayList<>();

    public ClassesViews(List<ClassModel> classesModel) {
        for(ClassModel classModel : classesModel){
            ClassView classView = new ClassView(classModel);
            classes.add(classView);
        }
    }

    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
