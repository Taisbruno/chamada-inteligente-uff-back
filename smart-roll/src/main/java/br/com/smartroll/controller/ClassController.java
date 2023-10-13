package br.com.smartroll.controller;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.service.ClassService;
import br.com.smartroll.view.StudentsView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class")
public class ClassController {

    @Autowired
    ClassService service;

    @GetMapping("/enrolled")
    public StudentsView getEnrolledStudents(Long idClass) {
        //Optional<ClassEntity> classroom = service.getClass(idClass);
        List<StudentModel> students = Arrays.asList(
                new StudentModel("11700830", "Taís Bruno", "tais", "password", null),
                new StudentModel("11700830", "Natália Bruno", "natalia", "password", null)
        );
        return new StudentsView(students);
    }
}
