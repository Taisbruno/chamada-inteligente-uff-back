package br.com.smartroll.controller;

import br.com.smartroll.exception.UsersNotFoundException;
import br.com.smartroll.model.StudentModel;
import br.com.smartroll.service.ClassService;
import br.com.smartroll.service.UserService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.StudentsView;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/students")
@Tag(name = "student-controller", description = "Controller responsável por requisições de alunos")
public class StudentController {

    @Autowired
    UserService service;

    @ApiOperation(value = "Retorna todos as alunos inscritos em uma determinada disciplina.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = {@ExampleObject(value = SwaggerExamples.GETENROLLEDSTUDENTS)})),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Status não utilizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @GetMapping(value = "/enrolled", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getEnrolledStudentsByClass(@Parameter(description = "Código da classe/disciplina", example = "code1") @RequestParam String codeClass, @Parameter(description = "Semestre de interesse", example = "2023.1") @RequestParam String semester) throws UsersNotFoundException {
        List<StudentModel> students = service.getEnrolledStudentsByClassCode(codeClass, semester);
        StudentsView studentsView = new StudentsView(students);
        return studentsView.toJson();
    }

}
