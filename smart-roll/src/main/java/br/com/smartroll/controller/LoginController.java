package br.com.smartroll.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.service.LoginService;
import br.com.smartroll.utils.SwaggerExamples;
import br.com.smartroll.view.UserView;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/login")
@Tag(name = "login-controller", description = "Controller responsável por requisições relacionadas a login e autenticação")
public class LoginController {

    @Autowired
    LoginService service;

    @ApiOperation(value = "Realiza autenticação do usuário baseado em seu cpf e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {
                    @ExampleObject(value = SwaggerExamples.GETUSER) })),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição") })
    @PostMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserView> authenticate(@RequestBody UserEntity user) {
        UserEntity userEntity = service.authenticateUser(user.cpf, user.password);

        if (userEntity != null) {
            return ResponseEntity.ok().body(new UserView(userEntity));
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "WRONG_USER_OR_PASSWORD");
        }
    }
}
