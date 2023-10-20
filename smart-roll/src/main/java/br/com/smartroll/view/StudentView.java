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

    /**
     * Construtor de StudentView com base em um modelo de estudante.
     * Inicializa a visualização do estudante usando os dados do modelo fornecido.
     *
     * @param studentModel O modelo de estudante que será transformado em uma visualização de estudante.
     */
    public StudentView(StudentModel studentModel) {
        this.registration = studentModel.registrationNumber;
        this.name = studentModel.name;
    }

    /**
     * Construtor alternativo de StudentView com base em um número de registro e um nome.
     * Permite a criação direta de uma visualização de estudante sem necessidade de um modelo completo.
     *
     * @param registrationNumber O número de registro do estudante.
     * @param name O nome do estudante.
     */
    public StudentView(String registrationNumber, String name) {
        this.registration = registrationNumber;
        this.name = name;
    }
}
