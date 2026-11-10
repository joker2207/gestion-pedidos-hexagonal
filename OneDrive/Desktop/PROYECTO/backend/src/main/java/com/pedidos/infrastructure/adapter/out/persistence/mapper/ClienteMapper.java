package com.pedidos.infrastructure.adapter.out.persistence.mapper;

import com.pedidos.domain.model.Cliente;
import com.pedidos.infrastructure.adapter.out.persistence.entity.ClienteEntity;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toDomain(ClienteEntity entity) {
        return new Cliente(entity.getId(), entity.getNombre(), entity.getEmail());
    }

    public ClienteEntity toEntity(Cliente cliente) {
        ClienteEntity entity = new ClienteEntity();
        entity.setId(cliente.getId());
        entity.setNombre(cliente.getNombre());
        entity.setEmail(cliente.getEmail());
        return entity;
    }
}
