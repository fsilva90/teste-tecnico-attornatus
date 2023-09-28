package com.testetecnicoattornatus.dto.error;

import io.swagger.v3.oas.annotations.media.Schema;

public record MensagemErro (
        @Schema(description = "Mensagem de erro", example = "Erro ao realizar operação.")
        String message
) {

}
