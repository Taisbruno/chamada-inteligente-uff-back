package br.com.smartroll.controller;

import br.com.smartroll.exception.RollNotFoundException;
import br.com.smartroll.service.RollService;
import br.com.smartroll.view.RollsView;
import br.com.smartroll.view.StopWatchView;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/roll")
@Tag(name = "roll-controller", description = "Controller responsável por requisições de chamadas")
public class RollController {

    @Autowired
    RollService service;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @ApiOperation(value = "Inicia a transmissão do cronômetro via WebSocket para o callId fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transmissão iniciada com sucesso", content = @Content(schema = @Schema(description = "A resposta não possui corpo. Inicia a transmissão de mensagens via WebSocket para o tópico /topic/time/{callId} cujo payload é um json como descrito no exemplo abaixo."))),
            @ApiResponse(responseCode = "404", description = "A chamada com o callId fornecido não foi encontrada"),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos ou ausentes"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição") })
    @GetMapping(value = "/startStopwatch", produces = MediaType.APPLICATION_JSON_VALUE)
    public void startStopwatch(@RequestParam String callId) throws RollNotFoundException {

        if(service.getRoll(Long.parseLong(callId)).isEmpty()){
            throw new RollNotFoundException(callId);
        }
        service.startCall(callId);

        new Thread(() -> {
            while (service.activeCalls.containsKey(callId)) {
                Duration duration = Duration.between(service.activeCalls.get(callId), LocalDateTime.now());
                long hours = duration.toHours();
                long minutes = duration.minusHours(hours).toMinutes();
                long seconds = duration.minusHours(hours).minusMinutes(minutes).getSeconds();

                StopWatchView stopwatch = new StopWatchView(String.format("%02d", hours), String.format("%02d", minutes), String.format("%02d", seconds));

                messagingTemplate.convertAndSend("/topic/time/" + callId, stopwatch.toJson());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e.getMessage());
                }
                System.out.println("Sending message for callId " + callId + ": " + stopwatch.toJson());
            }
        }).start();
    }

    @ApiOperation(value = "Interrompe a transmissão do cronômetro via WebSocket para o callId fornecido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Transmissão interrompida com sucesso", content = @Content(schema = @Schema(description = "A resposta não possui corpo. A transmissão de mensagens via WebSocket para o tópico /topic/time/{callId} será interrompida."))),
            @ApiResponse(responseCode = "400", description = "Parâmetros inválidos ou ausentes"),
            @ApiResponse(responseCode = "404", description = "A chamada com o callId fornecido não foi encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")
    })
    @GetMapping(value = "/stopStopwatch")
    public void stopStopwatch(@RequestParam String callId) throws RollNotFoundException {
        if(service.getRoll(Long.parseLong(callId)).isEmpty()){
            throw new RollNotFoundException(callId);
        }
        service.endCall(callId);
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
