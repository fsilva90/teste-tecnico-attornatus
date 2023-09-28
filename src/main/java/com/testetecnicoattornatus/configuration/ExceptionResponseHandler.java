package com.testetecnicoattornatus.configuration;

import com.testetecnicoattornatus.exception.EnderecoInexistenteException;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.dto.error.MensagemErro;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ExceptionResponseHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PessoaInexistenteException.class)
    public ResponseEntity<MensagemErro> handlePessoaInexistente(PessoaInexistenteException ex) {
        return new ResponseEntity<>(new MensagemErro(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnderecoInexistenteException.class)
    public ResponseEntity<MensagemErro> handleEnderecoInexistente(EnderecoInexistenteException ex) {
        return new ResponseEntity<>(new MensagemErro(ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
