package br.com.smartroll.controller;

import br.com.smartroll.dto.UpdatePresenceStatusDTO;
import br.com.smartroll.exception.*;
import br.com.smartroll.model.PresenceModel;
import br.com.smartroll.service.PresenceService;
import br.com.smartroll.utils.SwaggerExamples;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
     */
    @ApiOperation(value = "Realiza a submissão de presença em uma chamada e transmite via WebSocket a lista de alunos inscritos em tempo real ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Corpo da mensagem mal formado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição") })
    @PostMapping(value = "/create-presence", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postPresence(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo da presença em uma chamada a ser preenchido", example = SwaggerExamples.POSTPRESENCE) @RequestBody String requestBody) throws InvalidJsonException, RollNotFoundException, RollClosedException {
        JSONObject requestBodyJson = null;
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
        String medicalCertificate = requestBodyJson.getString("medicalCertificate");
        String message = requestBodyJson.getString("message");

        PresenceModel presenceModelModel = new PresenceModel(registration, rollId, medicalCertificate, message);
        presenceService.submitPresence(presenceModelModel);
    }

    @ApiOperation(value = "Updates the presence status for a student in a roll call")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Presence status updated successfully"),
            @ApiResponse(responseCode = "400", description = "Bad request due to invalid or missing input"),
            @ApiResponse(responseCode = "404", description = "Presence record not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping(value = "/update-presence-status")
    public ResponseEntity<String> updatePresenceStatus(
            @ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo do login a ser preenchido", example = SwaggerExamples.POSTPRESENCE)
            @Validated @RequestBody UpdatePresenceStatusDTO requestBodyDto) {

        Long presence_id = requestBodyDto.getPresence_id();
        Boolean presence_status = requestBodyDto.getPresence_status();

        if (presence_id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("presence_id cannot be null or empty.");
        }

        try {
            presenceService.updatePresenceStatus(presence_id, presence_status);
            return ResponseEntity.ok().build();
        } catch (PresenceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            // Optionally log the exception for debugging and analysis
            // logger.error("An unexpected error occurred", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }


}





