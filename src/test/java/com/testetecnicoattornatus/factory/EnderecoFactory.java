package com.testetecnicoattornatus.factory;

import com.testetecnicoattornatus.domain.Endereco;
import com.testetecnicoattornatus.dto.request.EnderecoRequest;
import com.testetecnicoattornatus.dto.response.EnderecoResponse;

public class EnderecoFactory {

    public static EnderecoRequest criarEnderecoRequest() {
        return EnderecoRequest.builder()
                .logradouro("Avenida Paulista")
                .cep("11111-111")
                .numero(1)
                .cidade("São Paulo")
                .isPrincipal(true)
                .idPessoa(1L)
                .build();
    }

    public static EnderecoRequest criarEnderecoRequestPost() {
        return EnderecoRequest.builder()
                .logradouro("Avenida Paulista")
                .cep("11111-111")
                .numero(1)
                .cidade("São Paulo")
                .isPrincipal(true)
                .idPessoa(1L)
                .build();
    }

    public static EnderecoRequest criarEnderecoRequestPut() {
        return EnderecoRequest.builder()
                .id(1L)
                .logradouro("Avenida Paulista")
                .cep("11111-111")
                .numero(1)
                .cidade("São Paulo")
                .isPrincipal(true)
                .idPessoa(1L)
                .build();
    }

    public static EnderecoResponse criarEnderecoResponse() {
        return EnderecoResponse.builder()
                .id(1L)
                .logradouro("Avenida Paulista")
                .cep("11111-111")
                .numero(1)
                .cidade("São Paulo")
                .isPrincipal(true)
                .pessoa(PessoaFactory.criarPessoaResponse())
                .build();
    }

    public static Endereco criarEnderecoEntity() {
        return Endereco.builder()
                .id(1L)
                .logradouro("Avenida Paulista")
                .cep("11111-111")
                .numero(1)
                .cidade("São Paulo")
                .isPrincipal(true)
                .pessoa(PessoaFactory.criarPessoaEntity())
                .build();
    }

}
