package com.testetecnicoattornatus.controller;

import com.testetecnicoattornatus.dto.request.PessoaRequest;
import com.testetecnicoattornatus.dto.response.PessoaResponse;
import com.testetecnicoattornatus.dto.error.MensagemErro;
import com.testetecnicoattornatus.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin("*")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    protected PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cadastra uma nova pessoa.", responses = @ApiResponse(responseCode = "201", description = "Se o cadastro for realizado com sucesso."))
    public ResponseEntity<PessoaResponse> criarPessoa(@RequestBody @Valid PessoaRequest pessoaRequest) {
        var newPessoa = this.pessoaService.criarPessoa(pessoaRequest);
        return new ResponseEntity<>(newPessoa, HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza uma pessoa.", responses = {
            @ApiResponse(responseCode = "204", description = "Se a pessoa existir no banco de dados."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Se a pessoa não existir no banco de dados.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MensagemErro.class)))
    })
    public ResponseEntity<Void> atualizarPessoa(@RequestBody @Valid PessoaRequest pessoaRequest) {
        this.pessoaService.atualizarPessoa(pessoaRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Lista todas as pessoas.", responses = @ApiResponse(responseCode = "200", description = "Se tudo ocorrer bem."))
    public ResponseEntity<Page<PessoaResponse>> listarPessoas(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(this.pessoaService.listarPessoa(pageable));
    }

    @GetMapping(value = "{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca uma pessoa pelo id informado.", responses = {
            @ApiResponse(responseCode = "200", description = "Se a pessoa existir no banco de dados."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Se o id da pessoa não existir no banco de dados.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MensagemErro.class)))
    })
    public ResponseEntity<PessoaResponse> buscarPessoarPorId(@PathVariable @Parameter(example = "1") Long idPessoa) {
        return ResponseEntity.ok(this.pessoaService.buscarPessoarPorId(idPessoa));
    }

}
