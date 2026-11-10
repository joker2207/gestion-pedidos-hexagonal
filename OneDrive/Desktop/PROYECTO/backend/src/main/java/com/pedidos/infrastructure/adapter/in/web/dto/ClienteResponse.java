package com.pedidos.infrastructure.adapter.in.web.dto;

import com.pedidos.domain.model.Cliente;

public record ClienteResponse(Long id, String nombre, String email) {

    public static ClienteResponse desde(Cliente cliente) {
        return new ClienteResponse(cliente.getId(), cliente.getNombre(), cliente.getEmail());
    }
}
