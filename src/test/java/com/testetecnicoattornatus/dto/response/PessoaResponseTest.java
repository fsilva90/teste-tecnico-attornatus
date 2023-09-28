package com.testetecnicoattornatus.dto.response;

import com.testetecnicoattornatus.factory.PessoaFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaResponseTest {


    private PessoaResponse pessoaResponse;
    private PessoaResponse pessoaResponseIgual;
    private PessoaResponse ppessoaResponseDiferente;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
    }

    private void inicializarObjetos() {
        this.pessoaResponse = PessoaFactory.criarPessoaResponse();
        this.pessoaResponseIgual = PessoaFactory.criarPessoaResponse();
        this.ppessoaResponseDiferente = PessoaResponse.builder().id(2L).build();
    }

    @Test
    void testGetAtributos() {
        Assertions.assertThat(this.pessoaResponse.getId()).isEqualTo(this.pessoaResponseIgual.getId());
        Assertions.assertThat(this.pessoaResponse.getNome()).isEqualTo(this.pessoaResponseIgual.getNome());
        Assertions.assertThat(this.pessoaResponse.getDataNascimento()).isEqualTo(this.pessoaResponseIgual.getDataNascimento());
    }

    @Test
    void testEquals() {
        Assertions.assertThat(this.pessoaResponse).isEqualTo(this.pessoaResponseIgual).isNotEqualTo(this.ppessoaResponseDiferente);
    }

    @Test
    void testHashCode() {
        Assertions.assertThat(this.pessoaResponse).hasSameHashCodeAs(this.pessoaResponseIgual);
    }

}
