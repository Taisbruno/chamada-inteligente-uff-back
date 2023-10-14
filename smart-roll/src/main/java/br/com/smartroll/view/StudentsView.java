package br.com.smartroll.view;

import br.com.smartroll.model.StudentModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsView {

    @SerializedName("classmates")
    private List<StudentView> students = new ArrayList<>();

    /**
     * Construtor padrão de uma view de estudantes.
     * @param studentsModel uma lista de estudantes.
     */
    public StudentsView(List<StudentModel> studentsModel) {
        for(StudentModel studentModel : studentsModel){
            StudentView studentView = new StudentView(studentModel.registrationNumber, studentModel.name);
            students.add(studentView);
        }
    }

    /**
     * Retorna a view em formato de Json.
     * @return Uma String com formatação de Json da view.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
