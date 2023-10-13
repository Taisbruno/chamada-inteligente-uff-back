package br.com.smartroll.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "class_subscription")
public class ClassSubscriptionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long id;
    @Column(name = "code", nullable = false, unique = true)
    public String classCode;
    @Column(name = "student_registration", nullable = false, unique = true)
    public String studentRegistration;
    @Column(name = "semester", nullable = false)
    public String semester;

    public ClassSubscriptionEntity() {
        // Construtor padrão
    }

    public ClassSubscriptionEntity(String classCode, String studentRegistration, String subscriptionDate) {
        this.classCode = classCode;
        this.studentRegistration = studentRegistration;
        this.semester = subscriptionDate;
    }
}
