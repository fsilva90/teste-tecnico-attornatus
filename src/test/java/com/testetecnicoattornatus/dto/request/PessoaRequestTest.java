package com.testetecnicoattornatus.dto.request;

import com.testetecnicoattornatus.factory.PessoaFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaRequestTest {

    private PessoaRequest pessoaRequest;
    private PessoaRequest pessoaRequestIgual;
    private PessoaRequest pessoaRequestDiferente;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
    }

    private void inicializarObjetos() {
        this.pessoaRequest = PessoaFactory.criarPessoaRequest();
        this.pessoaRequestIgual = PessoaFactory.criarPessoaRequest();
        this.pessoaRequestDiferente = PessoaRequest.builder().id(2L).build();
    }

    @Test
    void testGetAtributos() {
        Assertions.assertThat(this.pessoaRequest.getId()).isEqualTo(this.pessoaRequestIgual.getId());
        Assertions.assertThat(this.pessoaRequest.getNome()).isEqualTo(this.pessoaRequestIgual.getNome());
        Assertions.assertThat(this.pessoaRequest.getDataNascimento()).isEqualTo(this.pessoaRequestIgual.getDataNascimento());
    }

    @Test
    void testEquals() {
        Assertions.assertThat(this.pessoaRequest).isEqualTo(this.pessoaRequestIgual).isNotEqualTo(this.pessoaRequestDiferente);
    }

    @Test
    void testHashCode() {
        Assertions.assertThat(this.pessoaRequest).hasSameHashCodeAs(this.pessoaRequestIgual);
    }

}
