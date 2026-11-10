package com.pedidos.application.port.in;

import com.pedidos.domain.model.Cliente;

import java.util.List;

public interface GestionarClientesUseCase {

    Cliente crear(String nombre, String email);

    List<Cliente> listar();
}
