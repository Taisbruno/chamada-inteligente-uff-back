package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foram encontradas turmas de um determinado usuário não foram encontradas.
 */
public class ClassesNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe a matrícula usada para encontrar turmas do usuário.
     * @param registration Parâmetro utilizado para tentar obter as turmas do usuário que não foram encontradas.
     */
    public ClassesNotFoundException(String registration){
        super("No classes associated with registration: " + registration + " was found.");
    }
}
