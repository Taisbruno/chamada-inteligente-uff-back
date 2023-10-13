package br.com.smartroll.repository.entity;

import javax.persistence.*;

@Entity
@Table(name = "class")
public class ClassEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    public Long id;

    @Column(name = "code", nullable = false, unique = true, length = 50)
    public String code;

    @Column(name = "discipline", nullable = false, length = 100)
    public String discipline;

    @Column(name = "teacher", nullable = false, length = 100)
    public String teacher;

    @Column(name = "semester", nullable = false, length = 20)
    public String semester;

    @Column(name = "total", nullable = false)
    public int total;

    public ClassEntity() {
        // Construtor padrão
    }

    public ClassEntity(String code, String discipline, String teacher, String semester, int total) {
        this.code = code;
        this.discipline = discipline;
        this.teacher = teacher;
        this.semester = semester;
        this.total = total;
    }
}
