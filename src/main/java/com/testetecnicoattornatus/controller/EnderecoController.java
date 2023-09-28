package com.testetecnicoattornatus.controller;

import com.testetecnicoattornatus.dto.request.EnderecoRequest;
import com.testetecnicoattornatus.dto.response.EnderecoResponse;
import com.testetecnicoattornatus.dto.error.MensagemErro;
import com.testetecnicoattornatus.service.EnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@RestController
@RequestMapping("/enderecos")
@CrossOrigin("*")
public class EnderecoController {

    private final EnderecoService enderecoService;

    @Autowired
    protected EnderecoController(EnderecoService enderecoService) {
        this.enderecoService = enderecoService;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Cadastra um novo endereço para pessoa", responses = {
            @ApiResponse(responseCode = "204", description = "É necessario informar uma pessoa que esteja cadastrada no banco de dados"),
            @ApiResponse(
                    responseCode = "404",
                    description = "Se não existir uma pessoa com o id informado.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = MensagemErro.class)))
    })
    public ResponseEntity<EnderecoResponse> criarEndereco(@RequestBody @Valid EnderecoRequest enderecoRequest) {
        return new ResponseEntity<>(this.enderecoService.criarEndereco(enderecoRequest), HttpStatus.CREATED);
    }

    @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza o endereço principal da pessoa.", responses = {
            @ApiResponse(responseCode = "204", description = "Se o id da pessoa e o endereço existirem no banco de dados, e se o endereço pertencer a esta pessoa."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Se o endereço não existir, ou se a pessoa não for a dona do endereço informado.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MensagemErro.class)))
    })
    public ResponseEntity<Void> atualizarEndereco(@RequestBody @Valid EnderecoRequest enderecoRequest) {
        this.enderecoService.atualizarEndereco(enderecoRequest);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(value = "listar-por-pessoa/{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Busca os endereços de uma pessoa.", responses = {
            @ApiResponse(responseCode = "204", description = "Se o id da pessoa existir no banco de dados."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Se a pessoa não possuir endereços cadastrados no banco de dados.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MensagemErro.class)))
    })
    public ResponseEntity<List<EnderecoResponse>> listarEnderecoPorPessoa(@PathVariable @Parameter(example = "1") Long idPessoa) {
        return new ResponseEntity<>(this.enderecoService.listarEnderecoPorPessoa(idPessoa), HttpStatus.OK);
    }

    @GetMapping(value = "principal-por-pessoa/{idPessoa}", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Atualiza o endereço principal da pessoa.", responses = {
            @ApiResponse(responseCode = "204", description = "Se o id da pessoa e o endereço existirem no banco de dados, e se o endereço pertencer a esta pessoa."),
            @ApiResponse(
                    responseCode = "404",
                    description = "Se o endereço não existir, ou se a pessoa não for a dona do endereço informado.",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = MensagemErro.class)))
    })
    public ResponseEntity<EnderecoResponse> buscarEnderecoPrincipalPorPessoa(@PathVariable @Parameter(example = "1") Long idPessoa) {
        return new ResponseEntity<>(this.enderecoService.buscarEnderecoPrincipalPorPessoa(idPessoa), HttpStatus.OK);
    }



}
