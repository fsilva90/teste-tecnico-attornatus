package com.testetecnicoattornatus.service;

import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.request.PessoaRequest;
import com.testetecnicoattornatus.dto.response.PessoaResponse;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.factory.PessoaFactory;
import com.testetecnicoattornatus.mapper.PessoaMapper;
import com.testetecnicoattornatus.repository.PessoaRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class PessoaSerivceTest {

    @InjectMocks
    private PessoaService pessoaService;

    @Mock
    private PessoaRepository pessoaRepository;

    @Mock
    private PessoaMapper pessoaMapper;

    private Pessoa pessoaEntity;
    private PessoaRequest pessoaRequestPost;
    private PessoaRequest pessoaRequestPut;
    private PessoaResponse pessoaResponse;

    private Pageable pageable;

    private Page<Pessoa> pessoaPage;

    @BeforeEach
    void setUp() {
        this.iniciarObjetos();
        this.iniciarPageable();
        this.definirComportamentosParaMocks();
    }

    void iniciarObjetos() {
        this.pessoaEntity = PessoaFactory.criarPessoaEntity();
        this.pessoaRequestPost = PessoaFactory.criarPessoaRequestPost();
        this.pessoaRequestPut = PessoaFactory.criarPessoaRequestPut();
        this.pessoaResponse = PessoaFactory.criarPessoaResponse();
    }

    private void iniciarPageable() {
        List<Pessoa> pessoaList = List.of(this.pessoaEntity);
        this.pageable = PageRequest.of(0, 10);
        this.pessoaPage = new PageImpl<>(pessoaList, this.pageable, pessoaList.size());
    }

    private void definirComportamentosParaMocks() {
        when(pessoaMapper.converterRequestParaEntity(any(PessoaRequest.class))).thenReturn(pessoaEntity);
        when(pessoaMapper.converterEntityParaResponse(any(Pessoa.class))).thenReturn(pessoaResponse);

        when(pessoaRepository.save(any(Pessoa.class))).thenReturn(pessoaEntity);
        when(pessoaRepository.findById(any(Long.class))).thenReturn(Optional.of(pessoaEntity));
        when(pessoaRepository.findAll(pageable)).thenReturn(pessoaPage);
    }

    @Test
    @DisplayName("Deve listar pessoas corretamente")
    void testListarPessoa() {
        List<Pessoa> pessoas = List.of(pessoaEntity);
        Page<PessoaResponse> result = pessoaService.listarPessoa(pageable);

        verify(pessoaRepository).findAll(pageable);

        pessoas.forEach(p -> verify(pessoaMapper).converterEntityParaResponse(p));

        assertEquals(pessoas.size(), result.getContent().size());
    }

    @Test
    @DisplayName("Deve criar uma nova Pessoa")
    void testCriarPessoa() {
        PessoaResponse result = pessoaService.criarPessoa(pessoaRequestPost);

        verify(pessoaRepository, times(1)).save(any(Pessoa.class));
        verify(pessoaMapper, times(1)).converterEntityParaResponse(any(Pessoa.class));

        assertEquals("Teste", result.getNome());
    }

    @Test
    @DisplayName("Deve atualizar uma Pessoa existente")
    void testAtualizarPessoa() {
        pessoaService.atualizarPessoa(pessoaRequestPut);

        verify(pessoaMapper).atualizarDTOParaEntity(pessoaRequestPut, pessoaEntity);
        verify(pessoaRepository).save(pessoaEntity);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar atualizar uma Pessoa inexistente")
    void testAtualizarPessoa_thrownPessoaInexistenteException() {
        when(pessoaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(PessoaInexistenteException.class)
                .isThrownBy(() -> this.pessoaService.atualizarPessoa(pessoaRequestPost));
    }

    @Test
    @DisplayName("Deve buscar uma Pessoa por ID")
    void testBuscarPessoarPorId() {
        pessoaService.buscarPessoarPorId(any(Long.class));

        verify(pessoaRepository).findById(any(Long.class));
        verify(pessoaMapper).converterEntityParaResponse(pessoaEntity);
    }

    @Test
    @DisplayName("Deve lançar uma exceção ao tentar buscar uma Pessoa inexistente por ID")
    void testBuscarPessoarPorId_thrownPessoaInexistenteException() {
        when(pessoaRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThatExceptionOfType(PessoaInexistenteException.class)
                .isThrownBy(() -> this.pessoaService.buscarPessoarPorId(1L));
    }

}
