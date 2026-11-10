package com.pedidos.infrastructure.adapter.out.persistence;

import com.pedidos.application.port.out.ClienteRepositoryPort;
import com.pedidos.domain.model.Cliente;
import com.pedidos.infrastructure.adapter.out.persistence.mapper.ClienteMapper;
import com.pedidos.infrastructure.adapter.out.persistence.repository.ClienteJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ClienteJpaAdapter implements ClienteRepositoryPort {

    private final ClienteJpaRepository repository;
    private final ClienteMapper mapper;

    public ClienteJpaAdapter(ClienteJpaRepository repository, ClienteMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Cliente guardar(Cliente cliente) {
        return mapper.toDomain(repository.save(mapper.toEntity(cliente)));
    }

    @Override
    public Optional<Cliente> buscarPorId(Long id) {
        return repository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Cliente> listar() {
        return repository.findAll().stream().map(mapper::toDomain).toList();
    }
}
