package br.com.smartroll.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "user")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration", nullable = false, unique = true)
    public String registration;

    @Column(name = "name", nullable = false, length = 100)
    public String name;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    @Column(name = "type", nullable = false)
    public String type;

    public UserEntity() {
        // Construtor padrão
    }

    public UserEntity(String registration, String name, String email, String password, String type) {
        this.registration = registration;
        this.name = name;
        this.email = email;
        this.password = password;
        this.type = type;
    }
}
