package com.testetecnicoattornatus.dto.response;

import com.testetecnicoattornatus.factory.EnderecoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnderecoResponseTest {

    private EnderecoResponse enderecoResponse;
    private EnderecoResponse enderecoResponseIgual;
    private EnderecoResponse enderecoResponseDiferente;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
    }

    private void inicializarObjetos() {
        this.enderecoResponse = EnderecoFactory.criarEnderecoResponse();
        this.enderecoResponseIgual = EnderecoFactory.criarEnderecoResponse();
        this.enderecoResponseDiferente = EnderecoResponse.builder().id(2L).build();
    }

    @Test
    void testGetAtributos() {
        Assertions.assertThat(this.enderecoResponse.getId()).isEqualTo(this.enderecoResponseIgual.getId());
        Assertions.assertThat(this.enderecoResponse.getCep()).isEqualTo(this.enderecoResponseIgual.getCep());
        Assertions.assertThat(this.enderecoResponse.getLogradouro()).isEqualTo(this.enderecoResponseIgual.getLogradouro());
        Assertions.assertThat(this.enderecoResponse.getNumero()).isEqualTo(this.enderecoResponseIgual.getNumero());
        Assertions.assertThat(this.enderecoResponse.getCidade()).isEqualTo(this.enderecoResponseIgual.getCidade());
        Assertions.assertThat(this.enderecoResponse.isPrincipal()).isEqualTo(this.enderecoResponseIgual.isPrincipal());
        Assertions.assertThat(this.enderecoResponse.getPessoa()).isEqualTo(this.enderecoResponseIgual.getPessoa());
    }

    @Test
    void testEquals() {
        Assertions.assertThat(this.enderecoResponse).isEqualTo(this.enderecoResponseIgual).isNotEqualTo(this.enderecoResponseDiferente);
    }

    @Test
    void testHashCode() {
        Assertions.assertThat(this.enderecoResponse).hasSameHashCodeAs(this.enderecoResponseIgual);
    }
}
