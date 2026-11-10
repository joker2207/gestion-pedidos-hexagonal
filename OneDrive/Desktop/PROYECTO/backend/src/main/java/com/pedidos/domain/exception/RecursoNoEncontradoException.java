package com.pedidos.domain.exception;

public class RecursoNoEncontradoException extends RuntimeException {

    public RecursoNoEncontradoException(String recurso, Long id) {
        super("%s con id %d no encontrado".formatted(recurso, id));
    }
}
