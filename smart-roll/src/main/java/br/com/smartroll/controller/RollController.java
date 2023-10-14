package br.com.smartroll.controller;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.service.ClassService;
import br.com.smartroll.service.RollService;
import br.com.smartroll.view.RollsView;
import br.com.smartroll.view.StudentsView;
import io.swagger.annotations.ApiOperation;
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

import java.util.List;

@RestController
@RequestMapping("/roll")
@Tag(name = "roll-controller", description = "Controller responsável por requisições de chamadas")
public class RollController {

    @Autowired
    RollService service;
    /**
     * Retorna a contagem de tempo para cronômetro. VER SE ISSO PODE SER FEITO NO FRONTEND... ACHO QUE FICA MELHOR
     * @return
     */
    @GetMapping(value = "/nowTime", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTimeToStopwatch () {
        String s = "";
        return s;
    }

    @ApiOperation(value = "Retorna o histórico de chamadas de uma determinada turma - TODO.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = {@ExampleObject(value = "EXEMPLO AQUI - TODO")})),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Status não utilizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @GetMapping(value = "/rolls-historic/", produces = MediaType.APPLICATION_JSON_VALUE)
    public RollsView getRollsHistoricFromClass(){
        RollsView rollsView = new RollsView();
        return rollsView;
    }
}
