package br.com.smartroll.controller;

import br.com.smartroll.model.StudentModel;
import br.com.smartroll.view.RollsView;
import br.com.smartroll.view.StudentsView;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    /**
     * Retorna a contagem de tempo para cronômetro. VER SE ISSO PODE SER FEITO NO FRONTEND... ACHO QUE FICA MELHOR
     * @return
     */
    @GetMapping(value = "/nowTime", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getTimeToStopwatch () {
        String s = "";
        return s;
    }
}
