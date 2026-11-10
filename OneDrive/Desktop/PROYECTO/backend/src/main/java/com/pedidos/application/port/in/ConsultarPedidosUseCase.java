package com.pedidos.application.port.in;

import com.pedidos.domain.model.Pedido;

import java.util.List;

public interface ConsultarPedidosUseCase {

    Pedido obtenerPorId(Long id);

    List<Pedido> listar();
}
