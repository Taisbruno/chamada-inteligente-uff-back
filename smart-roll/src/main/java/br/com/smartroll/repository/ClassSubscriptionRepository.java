package br.com.smartroll.repository;

import br.com.smartroll.repository.entity.ClassSubscriptionEntity;
import br.com.smartroll.repository.interfaces.IClassSubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório que fornece uma camada de abstração sobre o repositório JPA IClassSubscriptionRepository.
 * Esta classe encapsula o acesso direto ao banco de dados e oferece métodos que podem ser personalizados
 * ou expandidos para adicionar funcionalidade adicional se necessário.
 */
@Repository
public class ClassSubscriptionRepository {

    @Autowired
    private IClassSubscriptionRepository classSubscriptionRepository;

    /**
     * Recupera todas as inscrições em classes armazenadas no banco de dados.
     *
     * @return uma lista de entidades ClassSubscriptionEntity representando todas as inscrições em classes.
     */
    public List<ClassSubscriptionEntity> getAllClassSubscriptions() {
        return classSubscriptionRepository.findAll();
    }

}
