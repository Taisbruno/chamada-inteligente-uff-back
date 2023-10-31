package br.com.smartroll.controller;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.service.PresenceService;
import br.com.smartroll.utils.SwaggerExamples;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador responsável por gerenciar as operações relacionadas às presenças.
 */
@RestController
@RequestMapping("/presences")
@Tag(name = "presence-controller", description = "Controller responsável por requisições de presenças")
public class PresenceController {

    @Autowired
    PresenceService presenceService;

    /**
     * Requisição para submeter uma presença em uma chamada. A lista de alunos
     * inscritos é transmitida em tempo real via WebSocket após a submissão.
     *
     * @param requestBody JSON que contém informações de presença.
     * @throws InvalidJsonException Quando o JSON do corpo da requisição é inválido.
     * @throws RollNotFoundException Quando a chamada especificada não é encontrada.
     * @throws RollClosedException Quando a chamada está fechada.
     * @throws StudentAlreadyPresentException Quando o aluno já está inscrito na chamada.
     * @throws UserNotFoundException Quando o aluno não foi encontrado.
     */
    @ApiOperation(value = "Realiza a submissão de presença ou falta justificada com atestado em uma chamada e transmite via WebSocket a lista de alunos inscritos em tempo real ", notes = "Submete uma presença ou falta justificada com atestato médico. Caso tenha sido passado um corpo de json sem a chave 'certificate', a presença será registrada com campo 'isPresent' como 'true', caso contrário, este campo será salvo como 'false' e esta presença deve ser validada pelo professor posteriormente para fins de abono de falta. <br><br>Exemplo de JSON sem atestado:<br><pre>" +
            "{<br>" +
            "    \"studentRegistration\": \"string\",<br>" +
            "    \"rollId\": \"string\",<br>" +
            "    \"message\": \"string\"<br>" +
            "}<br><pre>" +
            "<br><br>Exemplo de JSON com atestado:<br><pre>" +
            "{<br>" +
            "    \"studentRegistration\": \"string\",<br>" +
            "    \"certificate\": \"string\",<br>" +
            "    \"rollId\": \"string\",<br>" +
            "    \"message\": \"string\"<br>" +
            "}<br><pre>")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado ou chamada não encontrada"),
            @ApiResponse(responseCode = "400", description = "Corpo da mensagem mal formado"),
            @ApiResponse(responseCode = "409", description = "Aluno já inscrito na chamada ou chamada já fechada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição") })
    @PostMapping(value = "/create-presence", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postPresence(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo da presença em uma chamada a ser preenchido", example = SwaggerExamples.POSTPRESENCE) @RequestBody String requestBody) throws InvalidJsonException, RollNotFoundException, RollClosedException, StudentAlreadyPresentException, UserNotFoundException, StudentNotEnrolledInClassException {
        JSONObject requestBodyJson;
        try {
            if (requestBody != null) {
                requestBodyJson = new JSONObject(requestBody);
            } else throw new InvalidJsonException("missing.");
        }
        catch (JSONException e) {
            throw new InvalidJsonException(" incorrect format.");
        }

        if(!requestBodyJson.has("studentRegistration"))
            throw new InvalidJsonException("expected \"studentRegistration\" key.");
        if(requestBodyJson.isNull("studentRegistration"))
            throw new InvalidJsonException("\"studentRegistration\" can not be null.");
        if(!requestBodyJson.has("rollId"))
            throw new InvalidJsonException("expected \"rollId\" key.");
        if(requestBodyJson.isNull("rollId"))
            throw new InvalidJsonException("\"rollId\" can not be null.");
        if(!requestBodyJson.has("message"))
            throw new InvalidJsonException("expected \"message\" key.");
        if(requestBodyJson.isNull("message"))
            throw new InvalidJsonException("\"message\" can not be null.");

        String registration = requestBodyJson.getString("studentRegistration");
        String rollId = requestBodyJson.getString("rollId");
        String message = requestBodyJson.getString("message");

        PresenceModel presenceModel;

        // Se houver certificado no corpo do json, será submetido uma presença inválida pendente de análise a ser validada pelo professor.
        if(requestBodyJson.has("certificate")){
            String medicalCertificate = requestBodyJson.getString("certificate");
            presenceModel = new PresenceModel(registration, rollId, medicalCertificate, message);
        }else{ // Se não houver, será submetida uma presença válida.
            presenceModel = new PresenceModel(registration, rollId, message);
        }

        presenceService.submitPresence(presenceModel);
    }

    /**
     * Requisição para invalidar o status de presença de um aluno em uma chamada específica.
     *
     * Este método permite que um professor altere o status de presença
     * de um aluno para 'não presente'. Uma vez invocada, a API busca a presença correspondente com base
     * no ID fornecido e altera o status da presença para inválido.
     *
     * @param id O identificador único da presença que será invalidada.
     * @throws PresenceNotFoundException Se a presença com o ID fornecido não for encontrada.
     */
    @ApiOperation(value = "Invalida o status de presença de um aluno inscrito em uma chamada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "Status não utilizado"),
            @ApiResponse(responseCode = "404", description = "Presença não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")
    })
    @PatchMapping(value = "/invalidate-presence")
    public void invalidatePresenceStatus(@Parameter(description = "Id da presença", example = "1") @RequestParam String id) throws PresenceNotFoundException {
        presenceService.invalidatePresenceStatus(id);

    }

    /**
     * Requisição para validar o status de presença de um aluno que tenha inserido um atestado médico a ser validado pelo professor em uma chamada.
     *
     * Este método permite que um professor altere o status de presença
     * de um aluno para 'presente'. Uma vez invocada, a API busca a presença correspondente com base
     * no ID fornecido e altera o status da presença para válido.
     *
     * @param id O identificador único da presença que será validado.
     * @throws PresenceNotFoundException Se a presença com o ID fornecido não for encontrada.
     */
    @ApiOperation(value = "Valida o status de presença de um aluno inscrito em uma chamada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "Status não utilizado"),
            @ApiResponse(responseCode = "404", description = "Presença não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")
    })
    @PatchMapping(value = "/validate-presence")
    public void validatePresenceStatus(@Parameter(description = "Id da presença", example = "1") @RequestParam String id) throws PresenceNotFoundException {
        presenceService.validatePresenceStatus(id);

    }

    @ApiOperation(value = "Insere um atestado médico em uma presença de um aluno inscrito em uma chamada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "400", description = "Corpo do json mal formado"),
            @ApiResponse(responseCode = "404", description = "Presença não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")
    })
    @PatchMapping(value = "/insert-certificate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void insertCertificate(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo da da requisição a ser preenchido", example = SwaggerExamples.PUTCERTIFICATE) @RequestBody String requestBody) throws PresenceNotFoundException, InvalidJsonException {
        JSONObject requestBodyJson = null;
        try {
            if (requestBody != null) {
                requestBodyJson = new JSONObject(requestBody);
            } else throw new InvalidJsonException("missing.");
        }
        catch (JSONException | InvalidJsonException e) {
            throw new InvalidJsonException(" incorrect format.");
        }

        if(!requestBodyJson.has("id"))
            throw new InvalidJsonException("expected \"id\" key.");
        if(requestBodyJson.isNull("id"))
            throw new InvalidJsonException("\"id\" can not be null.");
        if(!requestBodyJson.has("certificate"))
            throw new InvalidJsonException("expected \"certificate\" key.");
        if(requestBodyJson.isNull("certificate"))
            throw new InvalidJsonException("\"certificate\" can not be null.");

        String id = requestBodyJson.getString("id");
        String certificate = requestBodyJson.getString("certificate");
        presenceService.updateCertificate(Long.parseLong(id), certificate);
    }

}





