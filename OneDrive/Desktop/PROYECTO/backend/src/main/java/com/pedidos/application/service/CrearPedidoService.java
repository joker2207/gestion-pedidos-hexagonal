package com.pedidos.application.service;

import com.pedidos.application.port.in.CrearPedidoUseCase;
import com.pedidos.application.port.out.ClienteRepositoryPort;
import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.domain.exception.RecursoNoEncontradoException;
import com.pedidos.domain.model.Cliente;
import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.model.Producto;

public class CrearPedidoService implements CrearPedidoUseCase {

    private final PedidoRepositoryPort pedidos;
    private final ClienteRepositoryPort clientes;
    private final ProductoRepositoryPort productos;

    public CrearPedidoService(PedidoRepositoryPort pedidos,
                              ClienteRepositoryPort clientes,
                              ProductoRepositoryPort productos) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.productos = productos;
    }

    @Override
    public Pedido crear(CrearPedidoCommand command) {
        Cliente cliente = clientes.buscarPorId(command.clienteId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", command.clienteId()));

        Pedido pedido = new Pedido(cliente);
        command.items().forEach(item -> agregarItem(pedido, item));

        return pedidos.guardar(pedido);
    }

    private void agregarItem(Pedido pedido, ItemCommand item) {
        Producto producto = productos.buscarPorId(item.productoId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Producto", item.productoId()));
        pedido.agregarLinea(producto, item.cantidad());
    }
}
