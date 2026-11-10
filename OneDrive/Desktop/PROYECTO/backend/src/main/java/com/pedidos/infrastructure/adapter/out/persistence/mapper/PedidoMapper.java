package com.pedidos.infrastructure.adapter.out.persistence.mapper;

import com.pedidos.domain.model.LineaPedido;
import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.model.Producto;
import com.pedidos.infrastructure.adapter.out.persistence.entity.PedidoEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PedidoMapper {

    private final ClienteMapper clienteMapper;
    private final ProductoMapper productoMapper;

    public PedidoMapper(ClienteMapper clienteMapper, ProductoMapper productoMapper) {
        this.clienteMapper = clienteMapper;
        this.productoMapper = productoMapper;
    }

    public Pedido toDomain(PedidoEntity entity) {
        // Un mismo producto debe ser una unica instancia dentro del pedido,
        // para que el stock se descuente sobre el mismo objeto.
        Map<Long, Producto> productos = new HashMap<>();
        List<LineaPedido> lineas = entity.getLineas().stream()
                .map(linea -> new LineaPedido(
                        productos.computeIfAbsent(linea.getProducto().getId(),
                                id -> productoMapper.toDomain(linea.getProducto())),
                        linea.getCantidad()))
                .toList();

        return Pedido.reconstruir(
                entity.getId(),
                clienteMapper.toDomain(entity.getCliente()),
                lineas,
                entity.getEstado());
    }
}
