package com.pedidos.application.service;

import com.pedidos.application.port.in.ConsultarPedidosUseCase;
import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.domain.exception.RecursoNoEncontradoException;
import com.pedidos.domain.model.Pedido;

import java.util.List;

public class ConsultarPedidosService implements ConsultarPedidosUseCase {

    private final PedidoRepositoryPort pedidos;

    public ConsultarPedidosService(PedidoRepositoryPort pedidos) {
        this.pedidos = pedidos;
    }

    @Override
    public Pedido obtenerPorId(Long id) {
        return pedidos.buscarPorId(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
    }

    @Override
    public List<Pedido> listar() {
        return pedidos.listar();
    }
}
