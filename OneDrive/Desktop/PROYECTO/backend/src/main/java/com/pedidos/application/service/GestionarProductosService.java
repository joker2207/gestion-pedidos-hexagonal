package com.pedidos.application.service;

import com.pedidos.application.port.in.GestionarProductosUseCase;
import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.domain.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public class GestionarProductosService implements GestionarProductosUseCase {

    private final ProductoRepositoryPort productos;

    public GestionarProductosService(ProductoRepositoryPort productos) {
        this.productos = productos;
    }

    @Override
    public Producto crear(String nombre, BigDecimal precio, int stock) {
        return productos.guardar(new Producto(null, nombre, precio, stock));
    }

    @Override
    public List<Producto> listar() {
        return productos.listar();
    }
}
