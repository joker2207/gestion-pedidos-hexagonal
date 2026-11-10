package com.pedidos.application.port.in;

import com.pedidos.domain.model.Pedido;

public interface CancelarPedidoUseCase {

    Pedido cancelar(Long pedidoId);
}
