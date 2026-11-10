package com.gestion.pedidos.domain.service;

import com.gestion.pedidos.domain.model.ItemPedido;
import com.gestion.pedidos.domain.model.Pedido;
import com.gestion.pedidos.domain.port.in.CrearPedidoUseCase;
import com.gestion.pedidos.domain.port.out.PedidoRepositoryPort;

import java.util.List;
import java.util.UUID;

public class PedidoService implements CrearPedidoUseCase {

    private final PedidoRepositoryPort pedidoRepositoryPort;

    public PedidoService(PedidoRepositoryPort pedidoRepositoryPort) {
        this.pedidoRepositoryPort = pedidoRepositoryPort;
    }

    @Override
    public Pedido crearPedido(String clienteId, List<ItemPedido> items) {
        Pedido nuevoPedido = new Pedido(UUID.randomUUID().toString(), clienteId);
        items.forEach(nuevoPedido::agregarItem);
        return pedidoRepositoryPort.guardar(nuevoPedido);
    }
}