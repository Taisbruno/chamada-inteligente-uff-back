package br.com.smartroll.view;

import br.com.smartroll.model.StudentModel;
import com.google.gson.annotations.SerializedName;

/**
 * Representa uma visualização simplificada de um estudante.
 * Esta classe é utilizada principalmente para serialização e apresentação dos dados de um estudante.
 */
public class StudentView {
    @SerializedName("registration")
    public String registration;

    @SerializedName("name")
    public String name;

    @SerializedName("frequency")
    public double frequency;

    @SerializedName("failed")
    public boolean failed;

    /**
     * Construtor de StudentView com base em um modelo de estudante.
     * Inicializa a visualização do estudante usando os dados do modelo fornecido.
     *
     * @param studentModel O modelo de estudante que será transformado em uma visualização de estudante.
     */
    public StudentView(StudentModel studentModel) {
        this.registration = studentModel.registrationNumber;
        this.name = studentModel.name;
        this.frequency = studentModel.frequency;
        this.failed = studentModel.failed;
    }
}
