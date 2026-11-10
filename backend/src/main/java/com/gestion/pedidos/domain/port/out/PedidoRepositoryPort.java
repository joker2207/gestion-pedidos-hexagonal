package com.gestion.pedidos.domain.port.out;

import com.gestion.pedidos.domain.model.Pedido;
import java.util.Optional;

public interface PedidoRepositoryPort {
    Pedido guardar(Pedido pedido);
    Optional<Pedido> buscarPorId(String id);
}