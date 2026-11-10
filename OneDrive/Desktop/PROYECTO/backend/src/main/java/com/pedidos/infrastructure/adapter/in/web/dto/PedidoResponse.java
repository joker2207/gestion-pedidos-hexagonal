package com.pedidos.infrastructure.adapter.in.web.dto;

import java.math.BigDecimal;
import java.util.List;

public record PedidoResponse(Long id,
                             Long clienteId,
                             String clienteNombre,
                             String estado,
                             List<LineaResponse> lineas,
                             BigDecimal subtotal,
                             BigDecimal descuento,
                             BigDecimal total) {

    public record LineaResponse(Long productoId,
                                String producto,
                                BigDecimal precioUnitario,
                                int cantidad,
                                BigDecimal subtotal) {
    }
}
