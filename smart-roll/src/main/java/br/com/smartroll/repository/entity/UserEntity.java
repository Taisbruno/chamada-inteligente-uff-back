package br.com.smartroll.repository.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "myuser")
public class UserEntity {
    @Id
    @Column(name = "registration", nullable = false, unique = true)
    public String registration;

    @Column(name = "name", nullable = false, length = 100)
    public String name;

    @Column(name = "cpf", nullable = false, unique = true)
    public String cpf;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "type", nullable = false)
    public String type;

    @OneToMany(mappedBy = "userEntity")
    private List<ClassSubscriptionEntity> classSubscriptions;

    public UserEntity() {
        // Construtor padrão
    }

    public UserEntity(String registration, String name, String cpf, String email, String password, String type) {
        this.registration = registration;
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
