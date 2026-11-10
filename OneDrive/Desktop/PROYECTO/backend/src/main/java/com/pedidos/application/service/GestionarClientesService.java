package com.pedidos.application.service;

import com.pedidos.application.port.in.GestionarClientesUseCase;
import com.pedidos.application.port.out.ClienteRepositoryPort;
import com.pedidos.domain.model.Cliente;

import java.util.List;

public class GestionarClientesService implements GestionarClientesUseCase {

    private final ClienteRepositoryPort clientes;

    public GestionarClientesService(ClienteRepositoryPort clientes) {
        this.clientes = clientes;
    }

    @Override
    public Cliente crear(String nombre, String email) {
        return clientes.guardar(new Cliente(null, nombre, email));
    }

    @Override
    public List<Cliente> listar() {
        return clientes.listar();
    }
}
