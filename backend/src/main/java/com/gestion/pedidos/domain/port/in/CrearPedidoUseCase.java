package com.gestion.pedidos.domain.port.in;

import com.gestion.pedidos.domain.model.ItemPedido;
import com.gestion.pedidos.domain.model.Pedido;
import java.util.List;

public interface CrearPedidoUseCase {
    Pedido crearPedido(String clienteId, List<ItemPedido> items);
}