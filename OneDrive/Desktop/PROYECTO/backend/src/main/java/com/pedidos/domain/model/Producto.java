package com.pedidos.domain.model;

import com.pedidos.domain.exception.StockInsuficienteException;

import java.math.BigDecimal;

public class Producto {

    private final Long id;
    private final String nombre;
    private final BigDecimal precio;
    private int stock;

    public Producto(Long id, String nombre, BigDecimal precio, int stock) {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }
        if (precio == null || precio.signum() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    public void descontarStock(int cantidad) {
        if (cantidad > stock) {
            throw new StockInsuficienteException(nombre, cantidad, stock);
        }
        this.stock -= cantidad;
    }

    public void reponerStock(int cantidad) {
        this.stock += cantidad;
    }

    public boolean tieneStockPara(int cantidad) {
        return cantidad <= stock;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }
    public int getStock() { return stock; }
}
