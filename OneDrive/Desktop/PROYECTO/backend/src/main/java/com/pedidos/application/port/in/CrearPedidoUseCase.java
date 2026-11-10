package com.pedidos.application.port.in;

import com.pedidos.domain.model.Pedido;

import java.util.List;

public interface CrearPedidoUseCase {

    Pedido crear(CrearPedidoCommand command);

    record CrearPedidoCommand(Long clienteId, List<ItemCommand> items) {
    }

    record ItemCommand(Long productoId, int cantidad) {
    }
}
