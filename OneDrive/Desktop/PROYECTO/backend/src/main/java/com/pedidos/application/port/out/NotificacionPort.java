package com.pedidos.application.port.out;

import com.pedidos.domain.model.Pedido;

public interface NotificacionPort {

    void notificarPedidoConfirmado(Pedido pedido);
}
