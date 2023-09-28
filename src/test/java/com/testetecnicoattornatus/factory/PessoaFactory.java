package com.testetecnicoattornatus.factory;

import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.request.PessoaRequest;
import com.testetecnicoattornatus.dto.response.PessoaResponse;

public class PessoaFactory {

    public static PessoaRequest criarPessoaRequest() {
        return PessoaRequest.builder()
                .id(1L)
                .nome("Teste")
                .dataNascimento("27-10-1990")
                .build();
    }
    public static PessoaRequest criarPessoaRequestPost() {
        return PessoaRequest.builder()
                .nome("Teste")
                .dataNascimento("27-10-1990")
                .build();
    }

    public static PessoaRequest criarPessoaRequestPut() {
        return PessoaRequest.builder()
                .id(1L)
                .nome("Teste")
                .dataNascimento("27-10-1990")
                .build();
    }

    public static PessoaResponse criarPessoaResponse() {
        return PessoaResponse.builder()
                .id(1L)
                .nome("Teste")
                .dataNascimento("27-10-1990")
                .build();
    }

    public static Pessoa criarPessoaEntity() {
        return Pessoa.builder()
                .id(1L)
                .nome("Teste")
                .dataNascimento("27-10-1990")
                .build();
    }
}
