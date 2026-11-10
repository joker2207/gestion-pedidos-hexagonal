package com.pedidos.domain.model;

import java.math.BigDecimal;

public class LineaPedido {

    private final Producto producto;
    private final int cantidad;

    public LineaPedido(Producto producto, int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero");
        }
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public BigDecimal subtotal() {
        return producto.getPrecio().multiply(BigDecimal.valueOf(cantidad));
    }

    public Producto getProducto() { return producto; }
    public int getCantidad() { return cantidad; }
}
