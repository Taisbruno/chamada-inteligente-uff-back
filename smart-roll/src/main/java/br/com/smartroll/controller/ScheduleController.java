package br.com.smartroll.controller;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.model.ScheduleModel;
import br.com.smartroll.service.ScheduleService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.RollView;
import br.com.smartroll.view.SchedulesView;
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

import java.util.List;

/**
 * Controlador responsável por gerenciar as operações relacionadas aos agendamentos.
 */
@RestController
@RequestMapping("/schedule")
@Tag(name = "schedule-controller", description = "Controller responsável por requisições de agendamento")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    /**
     * Requisição para submeter um agendamento relacionado a uma turma para criação automática de chamadas.
     * @param requestBody o corpo do json com agendamento.
     * @throws InvalidJsonException Exceção lançada quando json é inválido.
     * @throws InvalidDayOfWeekException Exceção lançada quando o dia da semana não está de acordo com o formato do CRON do Spring.
     * @throws InvalidTimeException Exceção lançada quando o tempo escolhido é inválido.
     * @throws InvalidTimeFormatException Exceção lançada quando o formato de tempo é inválido
     * @throws ScheduleConflictException Exceção lançada quando o agendamento criado conflita com o horário de um agendamento existente.
     * @throws ClassroomNotFoundException Exceção lançada quando a turma não foi encontrada.
     */
    @ApiOperation(value = "Submete um agendamento de criação automática de chamadas relacionado a uma turma.", notes = "Certifique-se de que os horários estejam no formato Time, como por exemplo '18:00:00', e de que o dia da semana seja um inteiro de acordo com o padrão da expressão CRON do Spring, a saber:  \n0 ou 7 - Domingo\n" +
            " 1 - Segunda-feira\n" +
            " 2 - Terça-feira\n" +
            " 3 - Quarta-feira\n" +
            " 4 - Quinta-feira\n" +
            " 5 - Sexta-feira\n" +
            " 6 - Sábado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "201", description = "Status não utilizado"),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "400", description = "Corpo do json mal formado; dia da semana, horário ou formato de horário incorretos"),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "409", description = "Agendamento já existente no horário escolhido"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @PostMapping(value = "/create-schedule/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postScheduleByClass(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo do agendamento a ser preenchido", example = SwaggerExamples.POSTSCHEDULE) @RequestBody String requestBody) throws InvalidJsonException, InvalidDayOfWeekException, InvalidTimeException, InvalidTimeFormatException, ScheduleConflictException, ClassroomNotFoundException {
        JSONObject requestBodyJson = null;
        try {
            if (requestBody != null) {
                requestBodyJson = new JSONObject(requestBody);
            } else throw new InvalidJsonException("missing.");
        }
        catch (JSONException | InvalidJsonException e) {
            throw new InvalidJsonException(" incorrect format.");
        }
        if(!requestBodyJson.has("classCode"))
            throw new InvalidJsonException("expected \"classCode\" key.");
        if(requestBodyJson.isNull("classCode"))
            throw new InvalidJsonException("\"classCode\" can not be null.");
        if(!requestBodyJson.has("dayOfWeek"))
            throw new InvalidJsonException("expected \"dayOfWeek\" key.");
        if(requestBodyJson.isNull("dayOfWeek"))
            throw new InvalidJsonException("\"dayOfWeek\" can not be null.");
        if(!requestBodyJson.has("startTime"))
            throw new InvalidJsonException("expected \"startTime\" key.");
        if(requestBodyJson.isNull("startTime"))
            throw new InvalidJsonException("\"startTime\" can not be null.");
        if(!requestBodyJson.has("endTime"))
            throw new InvalidJsonException("expected \"endTime\" key.");
        if(requestBodyJson.isNull("endTime"))
            throw new InvalidJsonException("\"endTime\" can not be null.");
        if(!requestBodyJson.has("longitude"))
            throw new InvalidJsonException("expected \"longitude\" key.");
        if(requestBodyJson.isNull("longitude"))
            throw new InvalidJsonException("\"longitude\" can not be null.");
        if(!requestBodyJson.has("latitude"))
            throw new InvalidJsonException("expected \"latitude\" key.");
        if(requestBodyJson.isNull("latitude"))
            throw new InvalidJsonException("\"latitude\" can not be null.");

        ScheduleModel scheduleModel = new ScheduleModel(requestBodyJson.getString("classCode"), requestBodyJson.getInt("dayOfWeek"), requestBodyJson.getString("startTime"), requestBodyJson.getString("endTime"), requestBodyJson.getString("longitude"), requestBodyJson.getString("latitude"));
        scheduleService.createSchedule(scheduleModel);
    }

    /**
     * Requisição para excluir um agendamento de chamadas de acordo com id.
     * @param idSchedule o id do agendamento.
     * @throws ScheduleNotFoundException agendamento não encontrado.
     */
    @ApiOperation(value = "Deleta um agendamento de chamadas de acordo com seu id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "204", description = "Status não utilizado"),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Agendamento não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @DeleteMapping(value = "/delete-schedule/")
    public void deleteScheduleById(@Parameter(description = "Id do agendamento", example = "1") @RequestParam String idSchedule) throws ScheduleNotFoundException {
        scheduleService.deleteScheduleById(idSchedule);
    }

    /**
     * Requisição para excluir todos os agendamentos de chamada relacionados a um código de turma.
     * @param codeClass código da turma.
     * @throws ClassroomNotFoundException turma não encontrada.
     */
    @ApiOperation(value = "Limpa todos os agendamento de chamadas relacionados a uma turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "204", description = "Status não utilizado"),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @DeleteMapping(value = "/clear-schedules/")
    public void clearAllScheduleByCodeClass(@Parameter(description = "Código da turma", example = "1") @RequestParam String codeClass) throws ClassroomNotFoundException {
        scheduleService.clearAllScheduleByCodeClass(codeClass);
    }

    /**
     * Requisição para retornar todos oa gendamentos de chamada relacionados a uma tuma.
     * @param codeClass código de turma.
     * @return Json representando os agendamentos relacionados à turma.
     * @throws ClassroomNotFoundException turma não encontrada.
     * @throws SchedulesNotFoundException angendamentos associados a essa turma não encontrados.
     */
    @ApiOperation(value = "Retorna todos os agendamento de chamadas relacionados a uma turma.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = String.class),
                    examples = {@ExampleObject(value = SwaggerExamples.GETSCHEDULES)})),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Turma não encontrada ou agendamentos associados a esta turma não encontrados"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @GetMapping(value = "/list-schedules/", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getAllScheduleByCodeClass(@Parameter(description = "Código da turma", example = "1") @RequestParam String codeClass) throws ClassroomNotFoundException, SchedulesNotFoundException {
        List<ScheduleModel> scheduleModels = scheduleService.getAllScheduleByCodeClass(codeClass);
        return new SchedulesView(scheduleModels).toJson();
    }
}
