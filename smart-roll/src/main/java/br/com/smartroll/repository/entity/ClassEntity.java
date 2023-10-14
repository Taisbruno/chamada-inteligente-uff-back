package br.com.smartroll.repository.entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "course_class")
public class ClassEntity {
    @Id
    @Column(name = "class_code", nullable = false, unique = true, length = 50)
    public String classCode;

    @Column(name = "discipline_code", nullable = false, unique = true, length = 50)
    public String disciplineCode;

    @Column(name = "discipline", nullable = false, length = 100)
    public String discipline;

    @Column(name = "teacher", nullable = false, length = 100)
    public String teacher;

    @Column(name = "semester", nullable = false, length = 20)
    public String semester;

    @Column(name = "total", nullable = false)
    public int total;

    @OneToMany(mappedBy = "classEntity")
    private List<ClassSubscriptionEntity> classSubscriptions;

    public ClassEntity() {
        // Construtor padrão
    }

    public ClassEntity(String code, String discipline, String teacher, String semester, int total) {
        this.disciplineCode = code;
        this.discipline = discipline;
        this.teacher = teacher;
        this.semester = semester;
        this.total = total;
    }
}
