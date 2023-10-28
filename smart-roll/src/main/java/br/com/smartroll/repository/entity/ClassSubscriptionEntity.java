package br.com.smartroll.repository.entity;

import javax.persistence.*;

/**
 * Representa a entidade "ClassSubscription" no banco de dados.
 */
@Entity
@Table(name = "classroom_subscription")
public class ClassSubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "registration", nullable = false, insertable = false, updatable = false)
    public String registration;
    @Column(name = "semester", nullable = false)
    public String semester;
    @ManyToOne
    @JoinColumn(name = "class_code", nullable = false, referencedColumnName = "class_code")
    public ClassEntity classEntity;
    @ManyToOne
    @JoinColumn(name = "registration", nullable = false, referencedColumnName = "registration")
    public UserEntity userEntity;

    /**
     * Construtor padrão de ClassSubscriptionEntity.
     */
    public ClassSubscriptionEntity() {
        // Construtor padrão
    }

    /**
     * Construtor parametrizado de ClassSubscriptionEntity.
     *
     * @param registration Número de registro do usuário (estudante).
     * @param subscriptionDate Data de inscrição do usuário na turma.
     */
    public ClassSubscriptionEntity(String registration, String subscriptionDate) {
        this.registration = registration;
        this.semester = subscriptionDate;
    }
}
