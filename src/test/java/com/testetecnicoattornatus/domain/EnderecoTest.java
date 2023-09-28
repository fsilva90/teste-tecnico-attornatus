package com.testetecnicoattornatus.domain;

import com.testetecnicoattornatus.factory.EnderecoFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EnderecoTest {

    private Endereco endereco;
    private Endereco enderecoIgual;
    private Endereco enderecoDiferente;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
    }

    private void inicializarObjetos() {
        this.endereco = EnderecoFactory.criarEnderecoEntity();
        this.enderecoIgual = EnderecoFactory.criarEnderecoEntity();
        this.enderecoDiferente = Endereco.builder().id(2L).build();
    }

    @Test
    void testGetAtributos() {
        Assertions.assertThat(this.endereco.getId()).isEqualTo(this.enderecoIgual.getId());
        Assertions.assertThat(this.endereco.getCep()).isEqualTo(this.enderecoIgual.getCep());
        Assertions.assertThat(this.endereco.getLogradouro()).isEqualTo(this.enderecoIgual.getLogradouro());
        Assertions.assertThat(this.endereco.getNumero()).isEqualTo(this.enderecoIgual.getNumero());
        Assertions.assertThat(this.endereco.getCidade()).isEqualTo(this.enderecoIgual.getCidade());
        Assertions.assertThat(this.endereco.isPrincipal()).isEqualTo(this.enderecoIgual.isPrincipal());
        Assertions.assertThat(this.endereco.getPessoa()).isEqualTo(this.enderecoIgual.getPessoa());
    }

    @Test
    void testEquals() {
        Assertions.assertThat(this.endereco).isEqualTo(this.enderecoIgual).isNotEqualTo(this.enderecoDiferente);
    }

    @Test
    void testHashCode() {
        Assertions.assertThat(this.endereco).hasSameHashCodeAs(this.enderecoIgual);
    }

    @Test
    void testToString() {
        Assertions.assertThat(this.endereco)
                .hasToString("Endereco(" +
                        "id=" + this.endereco.getId() +
                        ", logradouro=" + this.endereco.getLogradouro()  +
                        ", cep=" + this.endereco.getCep() +
                        ", numero=" + this.endereco.getNumero() +
                        ", cidade=" + this.endereco.getCidade() +
                        ", isPrincipal=" + this.endereco.isPrincipal() +
                        ", pessoa=" + this.endereco.getPessoa() +
                        ')');
    }

}
