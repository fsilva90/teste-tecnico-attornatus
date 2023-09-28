package com.testetecnicoattornatus.domain;

import com.testetecnicoattornatus.factory.PessoaFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PessoaTest {

    private Pessoa pessoa;
    private Pessoa pessoaIgual;
    private Pessoa pessoaDiferente;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
    }

    private void inicializarObjetos() {
        this.pessoa = PessoaFactory.criarPessoaEntity();
        this.pessoaIgual = PessoaFactory.criarPessoaEntity();
        this.pessoaDiferente = Pessoa.builder().id(2L).build();
    }

    @Test
    void testGetAtributos() {
        Assertions.assertThat(this.pessoa.getId()).isEqualTo(this.pessoaIgual.getId());
        Assertions.assertThat(this.pessoa.getNome()).isEqualTo(this.pessoaIgual.getNome());
        Assertions.assertThat(this.pessoa.getDataNascimento()).isEqualTo(this.pessoaIgual.getDataNascimento());
    }

    @Test
    void testEquals() {
        Assertions.assertThat(this.pessoa).isEqualTo(this.pessoaIgual).isNotEqualTo(this.pessoaDiferente);
    }

    @Test
    void testHashCode() {
        Assertions.assertThat(this.pessoa).hasSameHashCodeAs(this.pessoaIgual);
    }

    @Test
    void testToString() {
        Assertions.assertThat(this.pessoa)
                .hasToString("Pessoa(" +
                        "id=" + this.pessoa.getId() +
                        ", nome=" + this.pessoa.getNome() +
                        ", dataNascimento=" + this.pessoa.getDataNascimento() +
                        ')');
    }

}
