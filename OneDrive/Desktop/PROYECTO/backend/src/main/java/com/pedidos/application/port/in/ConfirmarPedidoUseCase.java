package com.pedidos.application.port.in;

import com.pedidos.domain.model.Pedido;

public interface ConfirmarPedidoUseCase {

    Pedido confirmar(Long pedidoId);
}
