package com.pedidos.infrastructure.adapter.out.persistence;

import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.domain.model.Producto;
import com.pedidos.infrastructure.adapter.out.persistence.mapper.ProductoMapper;
import com.pedidos.infrastructure.adapter.out.persistence.repository.ProductoJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ProductoJpaAdapter implements ProductoRepositoryPort {

    private final ProductoJpaRepository repository;
    private final ProductoMapper mapper;

    public ProductoJpaAdapter(ProductoJpaRepository repository, ProductoMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Producto guardar(Producto producto) {
        return mapper.toDomain(repository.save(mapper.toEntity(producto)));
    }

    @Override
    public Optional<Producto> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Producto> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
