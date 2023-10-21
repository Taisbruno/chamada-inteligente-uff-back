package br.com.smartroll.controller;

import br.com.smartroll.exception.IncorrectCredentialException;
import br.com.smartroll.exception.InvalidJsonException;
import br.com.smartroll.exception.UserUnauthorizedException;
import br.com.smartroll.view.UserView;
import io.swagger.annotations.ApiParam;
import kong.unirest.json.JSONException;
import kong.unirest.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import br.com.smartroll.repository.entity.UserEntity;
import br.com.smartroll.service.LoginService;
import br.com.smartroll.utils.SwaggerExamples;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Controlador responsável por gerenciar as operações de login e autenticação.
 */
@RestController
@RequestMapping("/login")
@Tag(name = "login-controller", description = "Controller responsável por requisições relacionadas a login e autenticação")
public class LoginController {

    @Autowired
    LoginService service;

    /**
     * Requisição para autenticar um usuário com base no seu CPF e senha.
     *
     * @param requestBody JSON que contém informações de login como CPF e senha.
     * @return Uma representação JSON do usuário autenticado.
     * @throws InvalidJsonException Quando o JSON do corpo da requisição é inválido.
     * @throws UserUnauthorizedException Quando o usuário não está autorizado.
     * @throws IncorrectCredentialException Quando as credenciais fornecidas são incorretas.
     */
    @ApiOperation(value = "Realiza autenticação do usuário baseado em seu cpf e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Requisição bem-sucedida", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class), examples = {
                    @ExampleObject(value = SwaggerExamples.GETUSER) })),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Corpo da mensagem mal formado"),
            @ApiResponse(responseCode = "500", description = "Erro interno na requisição") })
    @GetMapping(value = "/auth", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public String authenticate(@ApiParam(name = "requestBody", type = MediaType.APPLICATION_JSON_VALUE, value = "Corpo do login a ser preenchido", example = SwaggerExamples.POSTLOGIN) @RequestBody String requestBody) throws InvalidJsonException, UserUnauthorizedException, IncorrectCredentialException {
        JSONObject requestBodyJson = null;
        try {
            if (requestBody != null) {
                requestBodyJson = new JSONObject(requestBody);
            } else throw new InvalidJsonException("missing.");
        }
        catch (JSONException e) {
            throw new InvalidJsonException(" incorrect format.");
        }
        if(!requestBodyJson.has("cpf"))
            throw new InvalidJsonException("expected \"cpf\" key.");
        if(requestBodyJson.isNull("cpf"))
            throw new InvalidJsonException("\"cpf\" can not be null.");
        if(!requestBodyJson.has("password"))
            throw new InvalidJsonException("expected \"password\" key.");
        if(requestBodyJson.isNull("password"))
            throw new InvalidJsonException("\"password\" can not be null.");

        UserEntity userEntity = service.authenticateUser(requestBodyJson.get("cpf").toString(), requestBodyJson.get("password").toString());
        return new UserView(userEntity).toJson();
    }
}
