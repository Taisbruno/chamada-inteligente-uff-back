package br.com.smartroll.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/teachers")
@Tag(name = "teacher-controller", description = "Controller responsável por requisições de professores")
public class TeacherController {
}
