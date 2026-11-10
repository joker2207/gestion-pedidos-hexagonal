package com.pedidos.domain.exception;

public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String producto, int solicitado, int disponible) {
        super("Stock insuficiente para '%s': solicitado %d, disponible %d"
                .formatted(producto, solicitado, disponible));
    }
}
