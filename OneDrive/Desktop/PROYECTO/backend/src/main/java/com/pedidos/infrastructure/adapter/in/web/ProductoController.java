package com.pedidos.infrastructure.adapter.in.web;

import com.pedidos.application.port.in.GestionarProductosUseCase;
import com.pedidos.infrastructure.adapter.in.web.dto.ProductoRequest;
import com.pedidos.infrastructure.adapter.in.web.dto.ProductoResponse;
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
@RequestMapping("/api/productos")
public class ProductoController {

    private final GestionarProductosUseCase productos;

    public ProductoController(GestionarProductosUseCase productos) {
        this.productos = productos;
    }

    @GetMapping
    public List<ProductoResponse> listar() {
        return productos.listar().stream().map(ProductoResponse::desde).toList();
    }

    @PostMapping
    public ResponseEntity<ProductoResponse> crear(@Valid @RequestBody ProductoRequest request) {
        ProductoResponse creado = ProductoResponse.desde(
                productos.crear(request.nombre(), request.precio(), request.stock()));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }
}
