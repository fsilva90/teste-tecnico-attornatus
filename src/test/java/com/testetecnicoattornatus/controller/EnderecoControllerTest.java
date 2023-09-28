package com.testetecnicoattornatus.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testetecnicoattornatus.dto.request.EnderecoRequest;
import com.testetecnicoattornatus.dto.response.EnderecoResponse;
import com.testetecnicoattornatus.exception.EnderecoInexistenteException;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.service.EnderecoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
@WebMvcTest(EnderecoController.class)
class EnderecoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private EnderecoService enderecoService;

    private final String pathEndereco = "/enderecos";

    private EnderecoRequest request;
    private EnderecoResponse response;

    @BeforeEach
    void setUp() {
        this.inicializarObjetos();
        this.definirComportamentosParaMocks();
    }

    private void inicializarObjetos() {
        this.request = new EnderecoRequest();
        this.response = new EnderecoResponse();
    }

    private void definirComportamentosParaMocks() {
        when(enderecoService.criarEndereco(any(EnderecoRequest.class))).thenReturn(response);
        when(enderecoService.listarEnderecoPorPessoa(any(Long.class))).thenReturn(List.of(response));
        when(enderecoService.buscarEnderecoPrincipalPorPessoa(any(Long.class))).thenReturn(response);
    }


    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao criar endereço")
    void testCriarEnderecoComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .post(pathEndereco)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        verify(enderecoService, times(1)).criarEndereco(any(EnderecoRequest.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é NOT FOUND ao criar endereço")
    void testCriarEnderecoComErro() throws Exception {
        doThrow(new PessoaInexistenteException("Pessoa não encontrada"))
                .when(enderecoService).criarEndereco(any(EnderecoRequest.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(pathEndereco)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao listar endereços por pessoa")
    void testListarEnderecoPorPessoaComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(pathEndereco + "/listar-por-pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(enderecoService, times(1)).listarEnderecoPorPessoa(any(Long.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é NOT FOUND ao listar endereços por pessoa")
    void testListarEnderecoPorPessoaComErro() throws Exception {
        doThrow(new EnderecoInexistenteException("Endereço inexistente"))
                .when(enderecoService).listarEnderecoPorPessoa(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(pathEndereco + "/listar-por-pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao buscar endereço principal por pessoa")
    void testBuscarEnderecoPrincipalPorPessoaComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get(pathEndereco + "/principal-por-pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(enderecoService, times(1)).buscarEnderecoPrincipalPorPessoa(any(Long.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é NOT FOUND ao buscar endereço principal por pessoa")
    void testBuscarEnderecoPrincipalPorPessoaComErro() throws Exception {
        doThrow(new EnderecoInexistenteException("Endereço inexistente"))
                .when(enderecoService).buscarEnderecoPrincipalPorPessoa(any(Long.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(pathEndereco + "/principal-por-pessoa/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é OK ao atualizar endereço")
    void testAtualizarEnderecoComSucesso() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .put(pathEndereco)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNoContent());

        verify(enderecoService, times(1)).atualizarEndereco(any(EnderecoRequest.class));
    }

    @Test
    @DisplayName("Teste para verificar se o status HTTP é NOT FOUND ao atualizar endereço")
    void testAtualizarEnderecoComErro() throws Exception {
        doThrow(new PessoaInexistenteException("Pessoa não encontrada"))
                .when(enderecoService).atualizarEndereco(any(EnderecoRequest.class));

        mockMvc.perform(MockMvcRequestBuilders
                        .put(pathEndereco)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }

}
