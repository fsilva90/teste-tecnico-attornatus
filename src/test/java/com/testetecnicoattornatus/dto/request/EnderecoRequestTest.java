package com.testetecnicoattornatus.dto.request;

import com.testetecnicoattornatus.factory.EnderecoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnderecoRequestTest {

    private EnderecoRequest enderecoRequest;
    private EnderecoRequest enderecoRequestIgual;
    private EnderecoRequest enderecoRequestDiferente;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
    }

    private void inicializarObjetos() {
        this.enderecoRequest = EnderecoFactory.criarEnderecoRequest();
        this.enderecoRequestIgual = EnderecoFactory.criarEnderecoRequest();
        this.enderecoRequestDiferente = EnderecoRequest.builder().id(2L).build();
    }

    @Test
    void testGetAtributos() {
        Assertions.assertThat(this.enderecoRequest.getId()).isEqualTo(this.enderecoRequestIgual.getId());
        Assertions.assertThat(this.enderecoRequest.getCep()).isEqualTo(this.enderecoRequestIgual.getCep());
        Assertions.assertThat(this.enderecoRequest.getLogradouro()).isEqualTo(this.enderecoRequestIgual.getLogradouro());
        Assertions.assertThat(this.enderecoRequest.getNumero()).isEqualTo(this.enderecoRequestIgual.getNumero());
        Assertions.assertThat(this.enderecoRequest.getCidade()).isEqualTo(this.enderecoRequestIgual.getCidade());
        Assertions.assertThat(this.enderecoRequest.isPrincipal()).isEqualTo(this.enderecoRequestIgual.isPrincipal());
        Assertions.assertThat(this.enderecoRequest.getIdPessoa()).isEqualTo(this.enderecoRequestIgual.getIdPessoa());
    }

    @Test
    void testEquals() {
        Assertions.assertThat(this.enderecoRequest).isEqualTo(this.enderecoRequestIgual).isNotEqualTo(this.enderecoRequestDiferente);
    }

    @Test
    void testHashCode() {
        Assertions.assertThat(this.enderecoRequest).hasSameHashCodeAs(this.enderecoRequestIgual);
    }
}
