package com.pedidos.infrastructure.adapter.in.web.dto;

import com.pedidos.domain.model.Producto;

import java.math.BigDecimal;

public record ProductoResponse(Long id, String nombre, BigDecimal precio, int stock) {

    public static ProductoResponse desde(Producto producto) {
        return new ProductoResponse(producto.getId(), producto.getNombre(),
                producto.getPrecio(), producto.getStock());
    }
}
