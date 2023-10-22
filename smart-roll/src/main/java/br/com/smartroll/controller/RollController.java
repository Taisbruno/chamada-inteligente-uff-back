package br.com.smartroll.controller;

import br.com.smartroll.exception.InvalidJsonException;
import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.service.RollService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.RollView;
import br.com.smartroll.view.RollsView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por gerenciar as operações relacionadas às chamadas (rolls).
 */
@RestController
@RequestMapping("/roll")
@Tag(name = "roll-controller", description = "Controller responsável por requisições de chamadas")
public class RollController {

    @Autowired
    RollService service;

    /**
     * Requisição para finalizar uma chamada ativa. Registra a data e hora de conclusão.
     *
     * @param callId Id da chamada a ser finalizada.
     * @throws RollNotFoundException Caso a chamada especificada não seja encontrada.
     */
    @ApiOperation(value = "Finaliza uma chamada ativa, registrando a data e hora de conclusão.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos ou ausentes"),
            @ApiResponse(responseCode = "404", description = "A chamada com o callId fornecido não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")
    })
    @PutMapping(value = "/close-roll")
    public void closeRoll(@Parameter(description = "Id da chamada", example = "1") @RequestParam String callId) throws RollNotFoundException {
        service.closeRoll(Long.parseLong(callId));
    }

    /**
     * Requisição para retornar uma chamada.
     *
     * @param rollId Id da chamada a ser retornada.
     * @throws RollNotFoundException Caso a chamada especificada não seja encontrada.
     */
    @ApiOperation(value = "Retorna uma chamada com base no id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = {@ExampleObject(value = SwaggerExamples.GETROLLEXAMPLE)})),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos ou ausentes"),
            @ApiResponse(responseCode = "404", description = "A chamada com o rollId fornecido não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")
    })
    @GetMapping(value = "/get-roll", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getRoll(@Parameter(description = "Id da chamada", example = "1") @RequestParam String rollId) throws RollNotFoundException {
        RollModel rollModel = service.getRoll(Long.parseLong(rollId));
        return new RollView(rollModel).toJson();
    }


    /**
     * Requisição para recuperar o histórico de chamadas de uma turma específica.
     *
     * @return Uma visualização (RollsView) representando o histórico de chamadas.
     */
    @ApiOperation(value = "TODO - Retorna o histórico de chamadas de uma determinada turma.")
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
    public RollsView getRollsFromClass(){
        RollsView rollsView = new RollsView();
        return rollsView;
    }

    /**
     * Requisição para criar uma nova chamada associada a uma turma.
     * Processa o corpo da requisição para extrair e validar informações da chamada antes da criação.
     *
     * @param requestBody JSON representando a chamada a ser criada.
     * @throws InvalidJsonException Caso o corpo da requisição contenha JSON inválido ou ausente.
     */
    @ApiOperation(value = "Submete uma chamada relacionada a uma turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Status não utilizado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @PostMapping(value = "/create-roll/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postRollByClass(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo da chamada a ser preenchido", example = SwaggerExamples.POSTROLL) @RequestBody String requestBody) throws InvalidJsonException {
        JSONObject requestBodyJson = null;
        try {
            if (requestBody != null) {
                requestBodyJson = new JSONObject(requestBody);
            } else throw new InvalidJsonException("missing.");
        }
        catch (JSONException | InvalidJsonException e) {
            throw new InvalidJsonException(" incorrect format.");
        }
        if(!requestBodyJson.has("longitude"))
            throw new InvalidJsonException("expected \"longitude\" key.");
        if(requestBodyJson.isNull("longitude"))
            throw new InvalidJsonException("\"longitude\" can not be null.");
        if(!requestBodyJson.has("latitude"))
            throw new InvalidJsonException("expected \"latitude\" key.");
        if(requestBodyJson.isNull("latitude"))
            throw new InvalidJsonException("\"latitude\" can not be null.");
        if(!requestBodyJson.has("class_code"))
            throw new InvalidJsonException("expected \"class_code\" key.");
        if(requestBodyJson.isNull("class_code"))
            throw new InvalidJsonException("\"class_code\" can not be null.");
        RollModel rollModel = new RollModel(requestBodyJson.getString("longitude"), requestBodyJson.getString("latitude"), requestBodyJson.getString("class_code"));
        service.createRoll(rollModel);
    }

}
