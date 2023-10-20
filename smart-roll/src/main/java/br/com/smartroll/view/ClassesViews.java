package br.com.smartroll.view;

import br.com.smartroll.model.ClassModel;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe responsável por transformar uma lista de modelos de classe em um formato de visualização.
 * Além disso, fornece funcionalidades para serializar o objeto em formato JSON.
 */
public class ClassesViews {

    @SerializedName("classrooms")
    private List<ClassView> classes = new ArrayList<>();

    /**
     * Construtor da ClassesViews.
     * Transforma uma lista de modelos de classe em uma lista de visualizações de classe.
     *
     * @param classesModel Lista de modelos de classe que precisam ser transformados em visualizações.
     */
    public ClassesViews(List<ClassModel> classesModel) {
        for(ClassModel classModel : classesModel){
            ClassView classView = new ClassView(classModel);
            classes.add(classView);
        }
    }

    /**
     * Converte o objeto atual para uma representação JSON.
     *
     * @return String representando o objeto atual em formato JSON.
     */
    public String toJson() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
