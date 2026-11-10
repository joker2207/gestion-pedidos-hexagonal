package com.pedidos.infrastructure.adapter.out.persistence;

import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.domain.exception.RecursoNoEncontradoException;
import com.pedidos.domain.model.LineaPedido;
import com.pedidos.domain.model.Pedido;
import com.pedidos.infrastructure.adapter.out.persistence.entity.LineaPedidoEntity;
import com.pedidos.infrastructure.adapter.out.persistence.entity.PedidoEntity;
import com.pedidos.infrastructure.adapter.out.persistence.mapper.PedidoMapper;
import com.pedidos.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;
import com.pedidos.infrastructure.adapter.out.persistence.repository.PedidoJpaRepository;
import com.pedidos.infrastructure.adapter.out.persistence.repository.ProductoJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class PedidoJpaAdapter implements PedidoRepositoryPort {

    private final PedidoJpaRepository pedidos;
    private final ClienteJpaRepository clientes;
    private final ProductoJpaRepository productos;
    private final PedidoMapper mapper;

    public PedidoJpaAdapter(PedidoJpaRepository pedidos,
                            ClienteJpaRepository clientes,
                            ProductoJpaRepository productos,
                            PedidoMapper mapper) {
        this.pedidos = pedidos;
        this.clientes = clientes;
        this.productos = productos;
        this.mapper = mapper;
    }

    @Override
    public Pedido guardar(Pedido pedido) {
        PedidoEntity entity = pedido.getId() == null ? crearEntidad(pedido) : actualizarEntidad(pedido);
        return mapper.toDomain(pedidos.save(entity));
    }

    @Override
    public Optional<Pedido> buscarPorId(Long id) {
        return pedidos.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Pedido> listar() {
        return pedidos.findAllByOrderByIdDesc().stream().map(mapper::toDomain).toList();
    }

    private PedidoEntity crearEntidad(Pedido pedido) {
        PedidoEntity entity = new PedidoEntity();
        entity.setCliente(clientes.getReferenceById(pedido.getCliente().getId()));
        entity.setEstado(pedido.getEstado());
        pedido.getLineas().forEach(linea -> entity.getLineas().add(crearLinea(linea)));
        return entity;
    }

    private LineaPedidoEntity crearLinea(LineaPedido linea) {
        LineaPedidoEntity entity = new LineaPedidoEntity();
        entity.setProducto(productos.getReferenceById(linea.getProducto().getId()));
        entity.setCantidad(linea.getCantidad());
        return entity;
    }

    private PedidoEntity actualizarEntidad(Pedido pedido) {
        PedidoEntity entity = pedidos.findById(pedido.getId())
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", pedido.getId()));
        entity.setEstado(pedido.getEstado());
        return entity;
    }
}
