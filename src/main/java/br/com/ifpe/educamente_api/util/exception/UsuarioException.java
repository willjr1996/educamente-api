package br.com.ifpe.educamente_api.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
public class UsuarioException extends RuntimeException {
    public static final String MSG_PREFIXO_USUARIO = "Só é permitido usuários com um número de pernambuco";

    public UsuarioException(String msg) {
        super(String.format(msg));
    }
}