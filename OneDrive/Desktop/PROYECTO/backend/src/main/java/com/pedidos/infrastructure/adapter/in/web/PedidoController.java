package com.pedidos.infrastructure.adapter.in.web;

import com.pedidos.application.port.in.CancelarPedidoUseCase;
import com.pedidos.application.port.in.ConfirmarPedidoUseCase;
import com.pedidos.application.port.in.ConsultarPedidosUseCase;
import com.pedidos.application.port.in.CrearPedidoUseCase;
import com.pedidos.infrastructure.adapter.in.web.dto.PedidoRequest;
import com.pedidos.infrastructure.adapter.in.web.dto.PedidoResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Adaptador de entrada: solo traduce HTTP a casos de uso. No contiene reglas de negocio.
 */
@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    private final CrearPedidoUseCase crearPedido;
    private final ConfirmarPedidoUseCase confirmarPedido;
    private final CancelarPedidoUseCase cancelarPedido;
    private final ConsultarPedidosUseCase consultarPedidos;
    private final PedidoWebMapper mapper;

    public PedidoController(CrearPedidoUseCase crearPedido,
                            ConfirmarPedidoUseCase confirmarPedido,
                            CancelarPedidoUseCase cancelarPedido,
                            ConsultarPedidosUseCase consultarPedidos,
                            PedidoWebMapper mapper) {
        this.crearPedido = crearPedido;
        this.confirmarPedido = confirmarPedido;
        this.cancelarPedido = cancelarPedido;
        this.consultarPedidos = consultarPedidos;
        this.mapper = mapper;
    }

    @GetMapping
    public List<PedidoResponse> listar() {
        return consultarPedidos.listar().stream().map(mapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public PedidoResponse obtener(@PathVariable("id") Long id) {
        return mapper.toResponse(consultarPedidos.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<PedidoResponse> crear(@Valid @RequestBody PedidoRequest request) {
        PedidoResponse creado = mapper.toResponse(crearPedido.crear(mapper.toCommand(request)));
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @PostMapping("/{id}/confirmar")
    public PedidoResponse confirmar(@PathVariable("id") Long id) {
        return mapper.toResponse(confirmarPedido.confirmar(id));
    }

    @PostMapping("/{id}/cancelar")
    public PedidoResponse cancelar(@PathVariable("id") Long id) {
        return mapper.toResponse(cancelarPedido.cancelar(id));
    }
}
