package br.com.smartroll.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import br.com.smartroll.SmartRollsApplication;
import br.com.smartroll.exception.*;
import br.com.smartroll.view.ExceptionView;
import kong.unirest.json.JSONException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controlador responsável por interceptar as exceções atiradas pelos controllers e retorná-las como View em Json.
 */
@ControllerAdvice
@RestController
@ApiIgnore
public class ExceptionController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(SmartRollsApplication.class);

    /**
     * Método de fallback para retornar uma view genérica para quando a exceção não está mapeada
     * ou quando a URL requisitada não existe.
     * @return View com a informação da exceção gerada.
     */
    @RequestMapping("/error")
    public String error(){
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        ExceptionView view = null;
        if(response.getStatus() == HttpStatus.NOT_FOUND.value()){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            view = new ExceptionView(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.toString(), "Request " + request.getRequestURI() + " does not exist.");
        }
        else
            view = new ExceptionView(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Send a message to the development team with code 8991.");
        return view.toJson();
    }

    /**
     * Método que intercepta todas as exceções que devem retornar erro interno do servidor.
     * @param exception A exceção que foi intercepta.
     * @return A view da exceção com o estado 500 de erro interno.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = {RuntimeException.class, IOException.class, ServletException.class, InterruptedException.class})
    public ResponseEntity<String> internalError(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.INTERNAL_SERVER_ERROR.value(), HttpStatus.INTERNAL_SERVER_ERROR.name(), exception.getMessage());
        logger.warn("Status Response: " + HttpStatus.INTERNAL_SERVER_ERROR);
        return new ResponseEntity<>(view.toJson(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Método que retorna a view de todas as exceções que devem ser mapeados para "not authorized".
     * O erro 401 deve ser retornado quando as credenciais do usuário são inválidas ou não existentes.
     * @param exception A exceção mapeado.
     * @return A view da exceção com o estado 401 de not authorized.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = {UserUnauthorizedException.class, IncorrectCredentialException.class})
    public ResponseEntity<String>  unauthorized(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.name(), exception.getMessage());
        return new ResponseEntity<>(view.toJson(), HttpStatus.UNAUTHORIZED);
    }

    /**
     * Método que retorna a view de todas as exceções que devem ser mapeados para "não encontrado".
     * @param exception A exceção mapeado.
     * @return A view da exceção com o estado 404 de not found.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = {ClassesNotFoundException.class, UsersNotFoundException.class, RollNotFoundException.class, RollsNotFoundException.class, PresenceNotFoundException.class})
    public ResponseEntity<String> notFound(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.name(), exception.getMessage());
        return new ResponseEntity<>(view.toJson(), HttpStatus.NOT_FOUND);
    }


    /**
     * Método que mapeia as exceções de má requisição.
     * @param exception A exceção tratada.
     * @return A view da exceção com o estado 400 de bad request.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {JSONException.class, InvalidJsonException.class, RollClosedException.class})
    public ResponseEntity<String> badRequest(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.name(), exception.getMessage());
        return new ResponseEntity<>(view.toJson(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Método que mapeia as exceções de acesso proibido.
     * O erro 403 deve ser retornado quando as credenciais do usuário estão corretas, porém ele não
     * tem autorização o suficiente para recuperar o recurso solicitado.
     * @param exception A exceção tratada.
     * @return A view da exceção com o estado 403 de forbidden.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = {UserNotAuthorizedException.class})
    public ResponseEntity<String> forbidden(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.name(), exception.getMessage());
        return new ResponseEntity<>(view.toJson(), HttpStatus.FORBIDDEN);
    }

    /**
     * Método que retorna a view de todas as requisições que não são suportadas.
     * O erro 405 deve ser atirado quando a API entende a URL solicitada, mas simplesmente não
     * suporta a requisição pelo tipo chamado (GET, POST, DELETE).
     * @param exception A exceção tratada.
     * @return A view da exceção com o estado 405 de method not allowed.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(value = {HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<String> methodNotAllowed(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.METHOD_NOT_ALLOWED.value(), HttpStatus.METHOD_NOT_ALLOWED.name(), exception.getMessage());
        return new ResponseEntity<>(view.toJson(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * Método que mapeia as exceções de conflito.
     * O erro 409 deve ser retornado quando uma operação não pode ser realizada devido a um conflito
     * com o estado atual do recurso, como por exemplo, tentar registrar um estudante que já está presente ou tentar abrir uma chamada em uma turma que já contém chamada aberta.
     * @param exception A exceção tratada.
     * @return A view da exceção com o estado 409 de conflict.
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = {StudentAlreadyPresentException.class, ClassHasOpenRollException.class})
    public ResponseEntity<String> conflict(Exception exception){
        ExceptionView view = new ExceptionView(HttpStatus.CONFLICT.value(), HttpStatus.CONFLICT.name(), exception.getMessage());
        return new ResponseEntity<>(view.toJson(), HttpStatus.CONFLICT);
    }
}