package com.pedidos.application.service;

import com.pedidos.application.port.in.CancelarPedidoUseCase;
import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.domain.exception.RecursoNoEncontradoException;
import com.pedidos.domain.model.Pedido;

public class CancelarPedidoService implements CancelarPedidoUseCase {

    private final PedidoRepositoryPort pedidos;
    private final ProductoRepositoryPort productos;

    public CancelarPedidoService(PedidoRepositoryPort pedidos, ProductoRepositoryPort productos) {
        this.pedidos = pedidos;
        this.productos = productos;
    }

    @Override
    public Pedido cancelar(Long pedidoId) {
        Pedido pedido = pedidos.buscarPorId(pedidoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", pedidoId));

        pedido.cancelar();

        pedido.getLineas().forEach(linea -> productos.guardar(linea.getProducto()));
        return pedidos.guardar(pedido);
    }
}
