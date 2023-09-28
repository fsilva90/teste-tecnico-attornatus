package com.testetecnicoattornatus.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
@EqualsAndHashCode
@Builder
public class PessoaRequest {

    private Long id;

    @NotNull(message = "O campo nome não deve ser nulo.")
    private String nome;

    @NotNull(message = "O campo dataNascimento não deve ser nulo.")
    @Pattern(regexp = "\\d{2}-\\d{2}-\\d{4}", message = "Data de nascimento inválida")
    @Schema(description = "Data de nascimento", example = "01-01-2023")
    private String dataNascimento;

}
