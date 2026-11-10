package com.pedidos.application.service;

import com.pedidos.application.port.in.ConfirmarPedidoUseCase;
import com.pedidos.application.port.out.NotificacionPort;
import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.domain.exception.RecursoNoEncontradoException;
import com.pedidos.domain.model.Pedido;

public class ConfirmarPedidoService implements ConfirmarPedidoUseCase {

    private final PedidoRepositoryPort pedidos;
    private final ProductoRepositoryPort productos;
    private final NotificacionPort notificacion;

    public ConfirmarPedidoService(PedidoRepositoryPort pedidos,
                                  ProductoRepositoryPort productos,
                                  NotificacionPort notificacion) {
        this.pedidos = pedidos;
        this.productos = productos;
        this.notificacion = notificacion;
    }

    @Override
    public Pedido confirmar(Long pedidoId) {
        Pedido pedido = pedidos.buscarPorId(pedidoId)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", pedidoId));

        pedido.confirmar();

        pedido.getLineas().forEach(linea -> productos.guardar(linea.getProducto()));
        Pedido guardado = pedidos.guardar(pedido);
        notificacion.notificarPedidoConfirmado(guardado);
        return guardado;
    }
}
