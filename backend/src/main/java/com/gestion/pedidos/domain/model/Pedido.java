package com.gestion.pedidos.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {
    private String id;
    private String clienteId;
    private List<ItemPedido> items;
    private EstadoPedido estado;
    private BigDecimal total;
    private LocalDateTime fechaCreacion;

    public Pedido(String id, String clienteId) {
        this.id = id;
        this.clienteId = clienteId;
        this.items = new ArrayList<>();
        this.estado = EstadoPedido.PENDIENTE;
        this.total = BigDecimal.ZERO;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void agregarItem(ItemPedido item) {
        if (this.estado != EstadoPedido.PENDIENTE) {
            throw new IllegalStateException("No se pueden agregar ítems a un pedido que no está pendiente.");
        }
        this.items.add(item);
        recalcularTotal();
    }

    private void recalcularTotal() {
        this.total = items.stream()
                .map(ItemPedido::calcularSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void marcarComoPagado() {
        if (items.isEmpty()) {
            throw new IllegalStateException("No se puede pagar un pedido sin ítems.");
        }
        this.estado = EstadoPedido.PAGADO;
    }

    public String getId() { return id; }
    public String getClienteId() { return clienteId; }
    public List<ItemPedido> getItems() { return Collections.unmodifiableList(items); }
    public EstadoPedido getEstado() { return estado; }
    public BigDecimal getTotal() { return total; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
}