package com.testetecnicoattornatus.service;

import com.testetecnicoattornatus.dto.request.PessoaRequest;
import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.response.PessoaResponse;
import com.testetecnicoattornatus.mapper.PessoaMapper;
import com.testetecnicoattornatus.exception.PessoaInexistenteException;
import com.testetecnicoattornatus.repository.PessoaRepository;
import com.testetecnicoattornatus.util.ConstanteUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PessoaService {

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

    @Autowired
    public PessoaService(PessoaRepository pessoaRepository, PessoaMapper pessoaMapper) {
        this.pessoaRepository = pessoaRepository;
        this.pessoaMapper = pessoaMapper;
    }

    protected Optional<Pessoa> findOptionalPessoaById(Long idPessoa) {
        return this.pessoaRepository.findById(idPessoa);
    }

    /**
     * Lista pessoas com paginação.
     *
     * @param pageable Objeto de paginação
     * @return Página de respostas de pessoas
     */
    public Page<PessoaResponse> listarPessoa(Pageable pageable) {
        return pessoaRepository.findAll(pageable).map(this.pessoaMapper::converterEntityParaResponse);
    }

    /**
     * Cria uma nova pessoa.
     *
     * @param pessoaRequest DTO de pessoa a ser criada
     * @return Resposta da pessoa criada
     */
    @Transactional
    public PessoaResponse criarPessoa(PessoaRequest pessoaRequest) {
        var pessoaSerSalva = pessoaMapper.converterRequestParaEntity(pessoaRequest);
        return pessoaMapper.converterEntityParaResponse(this.pessoaRepository.save(pessoaSerSalva));
    }

    /**
     * Atualiza uma pessoa existente.
     *
     * @param pessoaRequest DTO de pessoa com os dados atualizados
     * @throws PessoaInexistenteException Se a pessoa não existe
     */
    @Transactional
    public void atualizarPessoa(PessoaRequest pessoaRequest) {
        Pessoa pessoaExistente = this.findOptionalPessoaById(pessoaRequest.getId())
                .orElseThrow(() -> new PessoaInexistenteException(String.format(ConstanteUtil.MENSAGEM_PESSOA_INEXISTENTE, pessoaRequest.getId())));

        this.pessoaMapper.atualizarDTOParaEntity(pessoaRequest, pessoaExistente);
        this.pessoaRepository.save(pessoaExistente);
    }

    /**
     * Busca uma pessoa por ID.
     *
     * @param idPessoa ID da pessoa a ser buscada
     * @return Resposta da pessoa encontrada
     * @throws PessoaInexistenteException Se a pessoa não existe
     */
    public PessoaResponse buscarPessoarPorId(Long idPessoa) {
        var pessoa = findOptionalPessoaById(idPessoa)
                .orElseThrow(() -> new PessoaInexistenteException(String.format(ConstanteUtil.MENSAGEM_PESSOA_INEXISTENTE, idPessoa)));
        return this.pessoaMapper.converterEntityParaResponse(pessoa);
    }

}
