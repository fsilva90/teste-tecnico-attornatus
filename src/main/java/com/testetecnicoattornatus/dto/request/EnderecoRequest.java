package com.testetecnicoattornatus.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class EnderecoRequest {

    @Schema(description = "Id do endereço", example = "1")
    private Long id;

    @Pattern(regexp = "[\\p{L}\\s]+", message = "Logradouro inválido. Por favor, informe um logradouro válido.")
    @Schema(description = "Logradouro", example = "Avenida Paulista")
    private String logradouro;

    @Pattern(regexp = "\\d{5}-\\d{3}", message = "CEP inválido. Formato permitido: 00000-000.")
    @Schema(description = "CEP", example = "11111-111")
    private String cep;

    @Schema(description = "Número", example = "1")
    private Integer numero;

    @Pattern(regexp = "[\\p{L}\\s]+", message = "Cidade inválida. Por favor, informe uma cidade válida.")
    @Schema(description = "Cidade", example = "São Paulo")
    private String cidade;

    @JsonProperty("principal")
    @Schema(description = "Identifica o endereço principal da pessoa", example = "true", defaultValue = "false")
    private boolean isPrincipal;

    @NotNull(message = "Id da pessoa inválido. Por favor informe um id da pessoa válido.")
    @Schema(description = "Id da entidade Pessoa", example = "1")
    private Long idPessoa;
}
