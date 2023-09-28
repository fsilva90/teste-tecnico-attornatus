package com.testetecnicoattornatus.service;

import com.testetecnicoattornatus.domain.Endereco;
import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.request.EnderecoRequest;
import com.testetecnicoattornatus.dto.response.EnderecoResponse;
import com.testetecnicoattornatus.exception.EnderecoInexistenteException;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.factory.EnderecoFactory;
import com.testetecnicoattornatus.factory.PessoaFactory;
import com.testetecnicoattornatus.mapper.EnderecoMapper;
import com.testetecnicoattornatus.repository.EnderecoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class EnderecoServiceTest {

    @InjectMocks
    private EnderecoService enderecoService;

    @Mock
    private PessoaService pessoaService;

    @Mock
    private EnderecoRepository enderecoRepository;

    @Mock
    private EnderecoMapper enderecoMapper;

    private Pessoa pessoaEntity;
    private Endereco enderecoEntity;
    private List<Endereco> enderecoPrincipalList;
    private EnderecoRequest enderecoRequestPost;
    private EnderecoRequest enderecoRequestPut;
    private EnderecoResponse enderecoResponse;

    @BeforeEach
    void setUp() {
        this.iniciarObjetos();
        this.definirComportamentosParaMocks();
    }

    void iniciarObjetos() {
        this.pessoaEntity = PessoaFactory.criarPessoaEntity();
        this.enderecoEntity = EnderecoFactory.criarEnderecoEntity();
        this.enderecoRequestPost = EnderecoFactory.criarEnderecoRequestPost();
        this.enderecoRequestPut = EnderecoFactory.criarEnderecoRequestPut();
        this.enderecoResponse = EnderecoFactory.criarEnderecoResponse();
        this.enderecoPrincipalList = List.of(enderecoEntity);
    }

    private void definirComportamentosParaMocks() {
        when(pessoaService.findOptionalPessoaById(any(Long.class))).thenReturn(Optional.of(pessoaEntity));

        when(enderecoMapper.converterRequestParaEntity(any(EnderecoRequest.class), any(Pessoa.class))).thenReturn(enderecoEntity);
        when(enderecoMapper.converterEntityParaResponse(any(Endereco.class))).thenReturn(enderecoResponse);

        when(enderecoRepository.findByPessoaAndPrincipal(any(Pessoa.class), any(Boolean.class))).thenReturn(enderecoPrincipalList);
        when(enderecoRepository.findByPessoa(any(Pessoa.class))).thenReturn(enderecoPrincipalList);
        when(enderecoRepository.findByEnderecoPrincipalPorPessoa(any(Long.class))).thenReturn(Optional.of(enderecoEntity));
        when(enderecoRepository.findEnderecoById(any(Long.class))).thenReturn(Optional.of(enderecoEntity));
        when(enderecoRepository.save(any(Endereco.class))).thenReturn(enderecoEntity);
    }

    @Test
    @DisplayName("Deve criar um novo Endereço")
    void testCriarEndereco() {
        EnderecoResponse result = enderecoService.criarEndereco(enderecoRequestPost);

        verify(pessoaService, times(1)).findOptionalPessoaById(any(Long.class));
        verify(enderecoMapper, times(1)).converterRequestParaEntity(any(EnderecoRequest.class), any(Pessoa.class));
        verify(enderecoMapper, times(1)).converterEntityParaResponse(any(Endereco.class));
        verify(enderecoRepository, times(1)).save(any(Endereco.class));

        assertEquals("Avenida Paulista", result.getLogradouro());
        assertTrue(result.isPrincipal());
    }

    @Test
    @DisplayName("Deve criar um novo Endereço com 'principal' igual a 'false'")
    void testCriarEnderecoComPrincipalFalse() {
        var enderecoResponse = this.enderecoResponse;
        enderecoResponse.setPrincipal(false);

        when(enderecoMapper.converterEntityParaResponse(any(Endereco.class))).thenReturn(enderecoResponse);

        EnderecoResponse result = enderecoService.criarEndereco(enderecoRequestPost);

        verify(pessoaService, times(1)).findOptionalPessoaById(any(Long.class));
        verify(enderecoMapper, times(1)).converterRequestParaEntity(any(EnderecoRequest.class), any(Pessoa.class));
        verify(enderecoMapper, times(1)).converterEntityParaResponse(any(Endereco.class));
        verify(enderecoRepository, times(1)).save(any(Endereco.class));

        assertEquals("Avenida Paulista", result.getLogradouro());
        assertFalse(result.isPrincipal());
    }

    @Test
    @DisplayName("Deve listar Endereços por Pessoa")
    void testListarEnderecoPorPessoa() {
        List<Endereco> enderecos = List.of(enderecoEntity);
        List<EnderecoResponse> resultado = enderecoService.listarEnderecoPorPessoa(any(Long.class));

        verify(pessoaService, times(1)).findOptionalPessoaById(any(Long.class));
        verify(enderecoRepository, times(1)).findByPessoa(any(Pessoa.class));
        enderecos.forEach(e -> verify(enderecoMapper).converterEntityParaResponse(e));

        assertEquals(enderecos.size(), resultado.size());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar listar Endereços por Pessoa inexistente")
    void testListarEnderecoPorPessoa_thrownPessoaInexistenteException() {
        when(pessoaService.findOptionalPessoaById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(PessoaInexistenteException.class)
                .isThrownBy(() -> this.enderecoService.listarEnderecoPorPessoa(1L));
    }

    @Test
    @DisplayName("Deve buscar o Endereço principal de uma Pessoa")
    void testBuscarEnderecoPrincipalPorPessoa() {
        EnderecoResponse resultado = enderecoService.buscarEnderecoPrincipalPorPessoa(any(Long.class));

        verify(enderecoRepository, times(1)).findByEnderecoPrincipalPorPessoa(any(Long.class));
        verify(enderecoMapper, times(1)).converterEntityParaResponse(any(Endereco.class));

        assertEquals(enderecoResponse.getPessoa().getId(), resultado.getPessoa().getId());
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar buscar o Endereço principal de uma Pessoa inexistente")
    void testBuscarEnderecoPrincipalPorPessoa_thrownEnderecoInexistenteException() {
        when(enderecoRepository.findByEnderecoPrincipalPorPessoa(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(EnderecoInexistenteException.class)
                .isThrownBy(() -> this.enderecoService.buscarEnderecoPrincipalPorPessoa(1L));
    }

    @Test
    @DisplayName("Deve atualizar um Endereço existente")
    void testAtualizarEndereco() {
        enderecoService.atualizarEndereco(enderecoRequestPut);

        verify(enderecoRepository).findEnderecoById(1L);
        verify(enderecoMapper).atualizarRequestParaEntity(enderecoRequestPut, enderecoEntity);
        verify(enderecoRepository).save(enderecoEntity);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar atualizar um Endereço inexistente")
    void testAtualizarEndereco_thrownEnderecoInexistenteException() {
        when(enderecoRepository.findEnderecoById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(EnderecoInexistenteException.class)
                .isThrownBy(() -> this.enderecoService.atualizarEndereco(enderecoRequestPut));
    }
}
