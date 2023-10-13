package br.com.smartroll.controller;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.service.ClassService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.RollsView;
import br.com.smartroll.view.StudentsView;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @ApiOperation(value = "Retorna todas as alunos inscritos em uma determinada disciplina.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = {@ExampleObject(value = SwaggerExamples.GETENROLLEDSTUDENTS)})),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Status não utilizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @GetMapping(value = "/enrolled/{disciplineCode}/{codeClass}/{semester}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public StudentsView getEnrolledStudentsInClass(@PathVariable String disciplineCode, @PathVariable String codeClass, @PathVariable String semester) {
        List<StudentModel> students = service.getEnrolledStudentsByClassCode(disciplineCode, codeClass, semester);
        return new StudentsView(students);
    }

    @GetMapping(value = "/rolls-historic/{disciplineCode}/{codeClass}/{semester}", produces = MediaType.APPLICATION_JSON_VALUE)
    public RollsView getRollsHistoric(){
        RollsView rollsView = new RollsView();
        return rollsView;
    }
}
