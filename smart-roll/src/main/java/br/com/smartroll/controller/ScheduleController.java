package br.com.smartroll.controller;

import br.com.smartroll.exception.*;
import br.com.smartroll.model.RollModel;
import br.com.smartroll.model.ScheduleModel;
import br.com.smartroll.service.ScheduleService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.RollView;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedule")
@Tag(name = "schedule-controller", description = "Controller responsável por requisições de agendamento")
public class ScheduleController {

    @Autowired
    ScheduleService scheduleService;

    @ApiOperation(value = "Submete um agendamento de chamada relacionado a uma turma.", notes = "Submete um agendamento para criação de chamadas automáticas para uma turma. Certifique-se de que os horários estejam no formato Time e de que o dia da semana seja um inteiro de acordo com o padrão da expressão CRON do Spring, a saber:  \n0 ou 7 - Domingo\n" +
            " 1 - Segunda-feira\n" +
            " 2 - Terça-feira\n" +
            " 3 - Quarta-feira\n" +
            " 4 - Quinta-feira\n" +
            " 5 - Sexta-feira\n" +
            " 6 - Sábado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida"),
            @ApiResponse(responseCode = "401", description = "Status não utilizado."),
            @ApiResponse(responseCode = "403", description = "Status não utilizado."),
            @ApiResponse(responseCode = "404", description = "Corpo do json mal formado"),
            @ApiResponse(responseCode = "409", description = "Chamada aberta já existente associada à turma"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição")})
    @PostMapping(value = "/create-schedule/", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void postScheduleByClass(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo do agendamento a ser preenchido", example = SwaggerExamples.POSTSCHEDULE) @RequestBody String requestBody) throws InvalidJsonException, ClassHasOpenRollException, InvalidDayOfWeekException, InvalidTimeException, InvalidTimeFormatException {
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
}
