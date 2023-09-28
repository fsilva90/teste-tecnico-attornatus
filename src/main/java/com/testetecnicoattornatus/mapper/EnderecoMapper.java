package com.testetecnicoattornatus.mapper;

import com.testetecnicoattornatus.domain.Endereco;
import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.request.EnderecoRequest;
import com.testetecnicoattornatus.dto.response.EnderecoResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnderecoMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public EnderecoMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Endereco converterRequestParaEntity(EnderecoRequest enderecoRequest, Pessoa pessoa) {
        Endereco endereco = modelMapper.map(enderecoRequest, Endereco.class);
        endereco.setPessoa(pessoa);
        return endereco;
    }

    public EnderecoResponse converterEntityParaResponse(Endereco endereco) {
        return modelMapper.map(endereco, EnderecoResponse.class);
    }

    public void atualizarRequestParaEntity(EnderecoRequest enderecoRequest, Endereco enderecoExistente) {
        if (enderecoRequest.getIdPessoa() == null) {
            enderecoRequest.setIdPessoa(enderecoExistente.getPessoa().getId());
        }
        this.modelMapper.map(enderecoRequest, enderecoExistente);
    }
}
