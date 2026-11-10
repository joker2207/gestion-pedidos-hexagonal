package com.pedidos.infrastructure.adapter.in.web;

import com.pedidos.application.port.in.GestionarClientesUseCase;
import com.pedidos.infrastructure.adapter.in.web.dto.ClienteRequest;
import com.pedidos.infrastructure.adapter.in.web.dto.ClienteResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final GestionarClientesUseCase clientes;

    public ClienteController(GestionarClientesUseCase clientes) {
        this.clientes = clientes;
    }

    @GetMapping
    public List<ClienteResponse> listar() {
        return clientes.listar().stream().map(ClienteResponse::desde).toList();
    }

    @PostMapping
    public ResponseEntity<ClienteResponse> crear(@Valid @RequestBody ClienteRequest request) {
        ClienteResponse creado = ClienteResponse.desde(clientes.crear(request.nombre(), request.email()));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
