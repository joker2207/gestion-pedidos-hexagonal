package com.pedidos.domain.service;

import java.math.BigDecimal;

public class DescuentoPorMonto implements PoliticaDescuento {

    private static final BigDecimal UMBRAL = new BigDecimal("500000");
    private static final BigDecimal PORCENTAJE = new BigDecimal("0.10");

    @Override
    public BigDecimal calcular(BigDecimal subtotal) {
        if (subtotal.compareTo(UMBRAL) > 0) {
            return subtotal.multiply(PORCENTAJE);
        }
        return BigDecimal.ZERO;
    }
}
