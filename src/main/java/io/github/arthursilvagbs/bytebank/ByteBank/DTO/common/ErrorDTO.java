package io.github.arthursilvagbs.bytebank.ByteBank.DTO.common;

import java.time.LocalDateTime;
import java.util.List;

public record ErrorDTO(
        int status,
        String erro,
        String mensagem,
        LocalDateTime timestamp
) {
    public static ErrorDTO of(int status, String erro, String mensagem) {
        return new ErrorDTO(status, erro, mensagem, LocalDateTime.now());
    }
}
