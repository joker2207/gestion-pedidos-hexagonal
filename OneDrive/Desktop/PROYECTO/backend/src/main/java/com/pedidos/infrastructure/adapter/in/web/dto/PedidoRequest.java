package com.pedidos.infrastructure.adapter.in.web.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record PedidoRequest(@NotNull Long clienteId,
                            @NotEmpty @Valid List<ItemRequest> items) {

    public record ItemRequest(@NotNull Long productoId, @Positive int cantidad) {
    }
}
