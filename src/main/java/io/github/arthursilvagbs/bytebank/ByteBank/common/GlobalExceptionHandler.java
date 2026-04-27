package io.github.arthursilvagbs.bytebank.ByteBank.common;

import io.github.arthursilvagbs.bytebank.ByteBank.DTO.common.ErrorDTO;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.OperacaoNaoPermitidaException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RecursoNaoEncontradoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.RegistroDuplicadoException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.SaldoInsuficienteException;
import io.github.arthursilvagbs.bytebank.ByteBank.exceptions.ValorInvalidoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<ErrorDTO> handleRecursoNaoEncontrado(RecursoNaoEncontradoException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDTO.of(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado", ex.getMessage()));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorDTO> handleEntityNotFound(EntityNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ErrorDTO.of(HttpStatus.NOT_FOUND.value(), "Recurso não encontrado", ex.getMessage()));
    }

    @ExceptionHandler(RegistroDuplicadoException.class)
    public ResponseEntity<ErrorDTO> handleRegistroDuplicado(RegistroDuplicadoException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ErrorDTO.of(HttpStatus.CONFLICT.value(), "Registro duplicado", ex.getMessage()));
    }

    @ExceptionHandler(SaldoInsuficienteException.class)
    public ResponseEntity<ErrorDTO> handleSaldoInsuficiente(SaldoInsuficienteException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorDTO.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Saldo insuficiente", ex.getMessage()));
    }

    @ExceptionHandler(OperacaoNaoPermitidaException.class)
    public ResponseEntity<ErrorDTO> handleOperacaoNaoPermitida(OperacaoNaoPermitidaException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ErrorDTO.of(HttpStatus.UNPROCESSABLE_ENTITY.value(), "Operação não permitida", ex.getMessage()));
    }

    @ExceptionHandler(ValorInvalidoException.class)
    public ResponseEntity<ErrorDTO> handleValorInvalido(ValorInvalidoException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.of(HttpStatus.BAD_REQUEST.value(), "Valor inválido", ex.getMessage()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.of(HttpStatus.BAD_REQUEST.value(), "Argumento inválido", ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleValidacao(MethodArgumentNotValidException ex) {
        String mensagem = ex.getBindingResult().getFieldErrors().stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDTO.of(HttpStatus.BAD_REQUEST.value(), "Erro de validação", mensagem));
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDTO> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ErrorDTO.of(HttpStatus.FORBIDDEN.value(), "Acesso negado", "Você não tem permissão para acessar este recurso."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGenerico(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorDTO.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno do servidor", "Ocorreu um erro inesperado."));
    }
}
