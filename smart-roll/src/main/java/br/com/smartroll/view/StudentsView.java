package br.com.smartroll.view;

import br.com.smartroll.model.StudentModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class StudentsView {

    @SerializedName("classmates")
    private final List<StudentView> students;

    /**
     * Construtor padrão de uma view de estudantes.
     * @param students uma lista de estudantes.
     */
    public StudentsView(Collection<StudentModel> students) {
        this.students = students.stream()
                .map(StudentView::new)
                .collect(Collectors.toList());

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
