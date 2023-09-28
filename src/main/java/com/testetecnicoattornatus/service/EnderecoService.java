package com.testetecnicoattornatus.service;

import com.testetecnicoattornatus.domain.Endereco;
import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.request.EnderecoRequest;
import com.testetecnicoattornatus.dto.response.EnderecoResponse;
import com.testetecnicoattornatus.exception.EnderecoInexistenteException;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.mapper.EnderecoMapper;
import com.testetecnicoattornatus.repository.EnderecoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.testetecnicoattornatus.util.ConstanteUtil.MENSAGEM_ENDERECO_INEXISTENTE;
import static com.testetecnicoattornatus.util.ConstanteUtil.MENSAGEM_ENDERECO_PRINCIPAL_INEXISTENTE;
import static com.testetecnicoattornatus.util.ConstanteUtil.MENSAGEM_PESSOA_INEXISTENTE;

@Service
public class EnderecoService {

    private final PessoaService pessoaService;
    private final EnderecoRepository enderecoRepository;
    private final EnderecoMapper enderecoMapper;

    @Autowired
    public EnderecoService(PessoaService pessoaService, EnderecoRepository enderecoRepository, EnderecoMapper enderecoMapper) {
        this.pessoaService = pessoaService;
        this.enderecoRepository = enderecoRepository;
        this.enderecoMapper = enderecoMapper;
    }

    /**
     * Busca uma pessoa pelo ID.
     *
     * @param idPessoa O ID da pessoa a ser buscada.
     * @return A pessoa encontrada.
     * @throws PessoaInexistenteException Se a pessoa não existir.
     */
    private Pessoa buscarPessoaPorId(Long idPessoa) {
        return this.pessoaService.findOptionalPessoaById(idPessoa)
                .orElseThrow(() -> new PessoaInexistenteException(String.format(MENSAGEM_PESSOA_INEXISTENTE, idPessoa)));
    }

    /**
     * Cria um novo endereço e, se necessário, desativa o endereço principal existente.
     *
     * @param enderecoRequest Os dados do novo endereço a ser criado.
     * @return O novo endereço criado.
     */
    @Transactional
    public EnderecoResponse criarEndereco(EnderecoRequest enderecoRequest) {
        var pessoaExistente = this.buscarPessoaPorId(enderecoRequest.getIdPessoa());

        if (enderecoRequest.isPrincipal()) {
            this.desativarEnderecoPrincipalExistente(pessoaExistente);
        }

        Endereco enderecoSerSalva = this.enderecoMapper.converterRequestParaEntity(enderecoRequest, pessoaExistente);
        return this.enderecoMapper.converterEntityParaResponse(this.enderecoRepository.save(enderecoSerSalva));
    }

    /**
     * Desativa o endereço principal existente para uma pessoa.
     *
     * @param pessoaExistente A pessoa para a qual o endereço principal será desativado.
     */
    private void desativarEnderecoPrincipalExistente(Pessoa pessoaExistente) {
        List<Endereco> enderecoPrincipalList = this.enderecoRepository.findByPessoaAndPrincipal(pessoaExistente, Boolean.TRUE);
        enderecoPrincipalList.forEach(endereco -> this.enderecoRepository.updateIsPrincipalById(Boolean.FALSE, endereco.getId()));
    }

    /**
     * Lista todos os endereços de uma pessoa.
     *
     * @param idPessoa O ID da pessoa para a qual os endereços serão listados.
     * @return Uma lista de endereços da pessoa.
     * @throws EnderecoInexistenteException Se nenhum endereço for encontrado para a pessoa.
     */
    public List<EnderecoResponse> listarEnderecoPorPessoa(Long idPessoa) {
        var pessoaExistente = this.buscarPessoaPorId(idPessoa);
        List<Endereco> personAddresses = this.enderecoRepository.findByPessoa(pessoaExistente);

        if (personAddresses.isEmpty()) {
            throw new EnderecoInexistenteException(String.format(MENSAGEM_ENDERECO_INEXISTENTE, idPessoa));
        }

        return personAddresses.stream()
                .map(this.enderecoMapper::converterEntityParaResponse)
                .toList();
    }

    /**
     * Busca o endereço principal de uma pessoa.
     *
     * @param idPessoa O ID da pessoa para a qual o endereço principal será buscado.
     * @return O endereço principal da pessoa.
     * @throws EnderecoInexistenteException Se não houver endereço principal para a pessoa.
     */
    public EnderecoResponse buscarEnderecoPrincipalPorPessoa(Long idPessoa) {
        Endereco endereco = this.enderecoRepository.findByEnderecoPrincipalPorPessoa(idPessoa)
                .orElseThrow(() -> new EnderecoInexistenteException(String.format(MENSAGEM_ENDERECO_PRINCIPAL_INEXISTENTE, idPessoa)));

        return this.enderecoMapper.converterEntityParaResponse(endereco);
    }

    /**
     * Atualiza um endereço existente.
     *
     * @param enderecoRequest Os novos dados do endereço.
     * @throws EnderecoInexistenteException Se o endereço não existir.
     */
    @Transactional
    public void atualizarEndereco(EnderecoRequest enderecoRequest) {
        Endereco enderecoExistente = this.enderecoRepository.findEnderecoById(enderecoRequest.getId())
                .orElseThrow(() -> new EnderecoInexistenteException(String.format(MENSAGEM_ENDERECO_PRINCIPAL_INEXISTENTE, enderecoRequest.getId())));

        if (enderecoRequest.isPrincipal()) {
            this.desativarEnderecoPrincipalExistente(enderecoExistente.getPessoa());
        }

        this.enderecoMapper.atualizarRequestParaEntity(enderecoRequest, enderecoExistente);
        this.enderecoRepository.save(enderecoExistente);
    }
}
