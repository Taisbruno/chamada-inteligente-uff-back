package br.com.smartroll.repository.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Representa a entidade "Class" no banco de dados.
 **/
@Entity
@Table(name = "classroom")
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

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    public List<ClassSubscriptionEntity> classSubscriptions;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    public List<RollEntity> rolls;

    @OneToMany(mappedBy = "classEntity", cascade = CascadeType.ALL)
    public List<ScheduleEntity> schedules;

    /**
     * Construtor padrão de ClassEntity.
     */
    public ClassEntity() {
        // Construtor padrão
    }

    /**
     * Construtor parametrizado de ClassEntity.
     *
     * @param code Código da disciplina.
     * @param discipline Nome da disciplina.
     * @param teacher Nome do professor.
     * @param semester Semestre em que a turma está ocorrendo.
     * @param total Número total de alunos na turma.
     */
    public ClassEntity(String code, String discipline, String teacher, String semester, int total) {
        this.disciplineCode = code;
        this.discipline = discipline;
        this.teacher = teacher;
        this.semester = semester;
        this.total = total;
    }
}
