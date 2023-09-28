package com.testetecnicoattornatus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PessoaInexistenteException extends RuntimeException {

    public PessoaInexistenteException(String message) {
        super(message);
    }
}
