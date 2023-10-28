package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foram encontrados usuários de uma determinada turma ou chamada.
 */
public class UsersNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o classCode e semester da turma usado para encontrar turmas do usuário.
     * @param classCode código da turma utilizado para tentar obter as turmas do usuário que não foram encontradas.
     * @param semester semestre utilizado para tentar obter as turmas do usuário que não foram encontradas.
     */
    public UsersNotFoundException(String classCode, String semester){
        super("No users associated with class: " + classCode + " and " + semester + " was found.");
    }

    /**
     * Construtor padrão que recebe o idRoll da chamada para encontrar a chamada.
     * @param idRoll id da chamada.
     */
    public UsersNotFoundException(String idRoll){
        super("No users associated with roll: " + idRoll + " was found.");
    }
}
