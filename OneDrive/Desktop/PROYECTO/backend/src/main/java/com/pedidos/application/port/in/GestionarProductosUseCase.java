package com.pedidos.application.port.in;

import com.pedidos.domain.model.Producto;

import java.math.BigDecimal;
import java.util.List;

public interface GestionarProductosUseCase {

    Producto crear(String nombre, BigDecimal precio, int stock);

    List<Producto> listar();
}
