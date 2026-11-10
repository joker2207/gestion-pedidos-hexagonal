package com.pedidos.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public record ProductoRequest(@NotBlank String nombre,
                              @NotNull @Positive BigDecimal precio,
                              @PositiveOrZero int stock) {
}
