package com.testetecnicoattornatus.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Builder
public class EnderecoResponse {

    @Schema(description = "Id do endereço", example = "1")
    private Long id;

    @Schema(description = "Logradouro", example = "Avenida Paulista")
    private String logradouro;

    @Schema(description = "CEP", example = "11111-111")
    private String cep;

    @Schema(description = "Número", example = "1")
    private Integer numero;

    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @JsonProperty("principal")
    @Schema(description = "Identifica o endereço principal da pessoa", example = "true")
    private boolean isPrincipal;

    @Schema(description = "Entidade Pessoa")
    private PessoaResponse pessoa;
}
