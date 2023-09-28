package com.testetecnicoattornatus.mapper;


import com.testetecnicoattornatus.domain.Pessoa;
import com.testetecnicoattornatus.dto.request.PessoaRequest;
import com.testetecnicoattornatus.dto.response.PessoaResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PessoaMapper {

	private final ModelMapper modelMapper;

	@Autowired
	public PessoaMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	public Pessoa converterRequestParaEntity(PessoaRequest pessoaRequest) {
		return modelMapper.map(pessoaRequest, Pessoa.class);
	}

	public PessoaResponse converterEntityParaResponse(Pessoa pessoa) {
		return modelMapper.map(pessoa, PessoaResponse.class);
	}

	public void atualizarDTOParaEntity(PessoaRequest pessoaRequest, Pessoa pessoa) {
		modelMapper.map(pessoaRequest, pessoa);
	}

}
