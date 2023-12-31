package br.com.smartroll.view;

import br.com.smartroll.model.ClassModel;
import br.com.smartroll.model.RollModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma visualização simplificada de uma classe.
 * Esta classe é utilizada principalmente para serialização e apresentação dos dados de uma classe.
 */
public class ClassView {

    @SerializedName("classCode")
    public String classCode;
    @SerializedName("disciplineCode")
    public String disciplineCode;
    @SerializedName("discipline")
    public String discipline;
    @SerializedName("teacher")
    public String teacher;
    @SerializedName("semester")
    public String semester;
    @SerializedName("total")
    public int total;
    @SerializedName("rolls")
    public List<RollView> rolls = new ArrayList<>();

    public ClassView(ClassModel classModel) {
        this.classCode = classModel.classCode;
        this.disciplineCode = classModel.disciplineCode;
        this.discipline = classModel.discipline;
        this.teacher = classModel.teacher;
        this.semester = classModel.semester;
        this.total = classModel.total;
        for(RollModel rollModel : classModel.rolls){
            RollView rollView = new RollView(rollModel);
            rolls.add(rollView);
        }
    }
}
