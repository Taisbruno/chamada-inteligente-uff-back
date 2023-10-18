package br.com.smartroll.exception;

/**
 * Exceção lançada quando não foram encontrados usuários de uma determinada turma.
 */
public class UsersNotFoundException extends Exception{

    /**
     * Construtor padrão que recebe o username usado para encontrar turmas do usuário.
     * @param classCode código da turma utilizado para tentar obter as turmas do usuário que não foram encontradas.
     * @param semester semestre utilizado para tentar obter as turmas do usuário que não foram encontradas.
     */
    public UsersNotFoundException(String classCode, String semester){
        super("No users associated with class: " + classCode + " and " + semester + " was found.");
    }
}
