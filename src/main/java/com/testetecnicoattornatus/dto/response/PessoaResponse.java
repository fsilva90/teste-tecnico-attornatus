package com.testetecnicoattornatus.dto.response;

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
public class PessoaResponse {

    private Long id;

    @Schema(description = "Nome da pessoa", example = "Felipe")
    private String nome;

    @Schema(description = "Data de nascimento", example = "01-01-2023")
    private String dataNascimento;

}
