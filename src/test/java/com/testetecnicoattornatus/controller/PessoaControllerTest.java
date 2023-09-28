package com.testetecnicoattornatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testetecnicoattornatus.dto.request.PessoaRequest;
import com.testetecnicoattornatus.dto.response.PessoaResponse;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.service.PessoaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(PessoaController.class)
class PessoaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PessoaService pessoaService;

    private final String pathPessoa = "/pessoas";

    private Page<PessoaResponse> page;
    private PessoaRequest request;
    private PessoaResponse response;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
        this.definirComportamentosParaMocks();
    }

    private void inicializarObjetos() {
        this.page = new PageImpl<>(Collections.emptyList());
        this.request = new PessoaRequest();
        this.response = new PessoaResponse();
    }

    private void definirComportamentosParaMocks() {
        when(pessoaService.listarPessoa(any())).thenReturn(page);
        when(pessoaService.criarPessoa(any(PessoaRequest.class))).thenReturn(response);
        when(pessoaService.buscarPessoarPorId(any(Long.class))).thenReturn(response);
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao listar pessoas")
    void testListarPessoasComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(pathPessoa)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(pessoaService, times(1)).listarPessoa(any());
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao criar pessoa")
    void testCriarPessoaComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post(pathPessoa)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(pessoaService, times(1)).criarPessoa(any(PessoaRequest.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao atualizar pessoa")
    void testAtualizarPessoaComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(pathPessoa)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(pessoaService, times(1)).atualizarPessoa(any(PessoaRequest.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é NOT FOUND ao atualizar pessoa")
    void testAtualizarPessoaComErro() throws Exception {
        doThrow(new PessoaInexistenteException("Pessoa não encontrada"))
                .when(pessoaService).atualizarPessoa(any(PessoaRequest.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(pathPessoa)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao buscar pessoa por ID")
    void testBuscarPessoarPorIdComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(pathPessoa + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        verify(pessoaService, times(1)).buscarPessoarPorId(any(Long.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é NOT FOUND ao buscar pessoa por ID")
    void testBuscarPessoarPorIdComErro() throws Exception {
        when(pessoaService.buscarPessoarPorId(any(Long.class))).thenThrow(new PessoaInexistenteException("Pessoa não encontrada"));

        mockMvc.perform(MockMvcRequestBuilders.get(pathPessoa + "/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
    }

}