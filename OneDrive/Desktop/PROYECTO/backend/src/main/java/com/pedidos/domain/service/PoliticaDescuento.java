package com.pedidos.domain.service;

import java.math.BigDecimal;

public interface PoliticaDescuento {

    BigDecimal calcular(BigDecimal subtotal);
}
