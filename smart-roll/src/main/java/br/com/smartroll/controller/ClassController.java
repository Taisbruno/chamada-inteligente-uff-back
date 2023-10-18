package br.com.smartroll.controller;

import br.com.smartroll.exception.ClassesNotFoundException;
import br.com.smartroll.model.ClassModel;
import br.com.smartroll.model.StudentModel;
import br.com.smartroll.repository.entity.ClassEntity;
import br.com.smartroll.service.ClassService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.ClassesViews;
import br.com.smartroll.view.RollsView;
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
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/class")
@Tag(name = "class-controller", description = "Controller responsável por requisições de turmas")
public class ClassController {

    @Autowired
    ClassService service;

    @ApiOperation(value = "Retorna todas as turmas de um determinado usuário.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = {@ExampleObject(value = SwaggerExamples.GETCLASSESBYUSER)})),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Status não utilizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @GetMapping(value = "/classes", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getClassesByUser(@Parameter(description = "Matrícula", example = "1") @RequestParam String registration) throws ClassesNotFoundException {
        List<ClassModel> classesModels = service.getClassesByUser(registration);
        ClassesViews classesViews = new ClassesViews(classesModels);
        return classesViews.toJson();
    }

}
