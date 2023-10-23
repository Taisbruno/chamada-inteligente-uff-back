package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.repository.interfaces.IClassRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório que fornece uma camada de abstração sobre o repositório JPA IClassRepository.
 * Esta classe encapsula o acesso direto ao banco de dados e oferece métodos que podem ser personalizados
 * ou expandidos para adicionar funcionalidade adicional se necessário.
 */
@Repository
public class ClassRepository {

    @Autowired
    private IClassRepository classRepository;


    /**
     * Localiza uma lista de classes com base na matrícula do usuário.
     *
     * @param registration a matrícula do usuário para o qual se deseja buscar as classes associadas.
     * @return uma lista de entidades ClassEntity associadas à matrícula do usuário fornecida.
     */
    public List<ClassEntity> findClassesByUserRegistration(String registration) {
        return classRepository.findClassesByUserRegistration(registration);
    }

}
