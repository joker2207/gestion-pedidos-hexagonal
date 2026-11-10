package com.pedidos.domain.model;

import com.pedidos.domain.exception.PedidoInvalidoException;
import com.pedidos.domain.exception.StockInsuficienteException;
import com.pedidos.domain.service.PoliticaDescuento;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Pedido {

    private Long id;
    private final Cliente cliente;
    private final List<LineaPedido> lineas = new ArrayList<>();
    private EstadoPedido estado = EstadoPedido.CREADO;

    public Pedido(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("El pedido requiere un cliente");
        }
        this.cliente = cliente;
    }

    public void agregarLinea(Producto producto, int cantidad) {
        validarModificable();
        if (!producto.tieneStockPara(cantidad)) {
            throw new StockInsuficienteException(
                    producto.getNombre(), cantidad, producto.getStock());
        }
        lineas.add(new LineaPedido(producto, cantidad));
    }

    public void confirmar() {
        validarModificable();
        if (lineas.isEmpty()) {
            throw new PedidoInvalidoException("No se puede confirmar un pedido vacío");
        }
        lineas.forEach(l -> l.getProducto().descontarStock(l.getCantidad()));
        estado = EstadoPedido.CONFIRMADO;
    }

    public void cancelar() {
        if (estado == EstadoPedido.CANCELADO) {
            throw new PedidoInvalidoException("El pedido ya está cancelado");
        }
        if (estado == EstadoPedido.CONFIRMADO) {
            lineas.forEach(l -> l.getProducto().reponerStock(l.getCantidad()));
        }
        estado = EstadoPedido.CANCELADO;
    }

    public BigDecimal subtotal() {
        return lineas.stream()
                .map(LineaPedido::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public BigDecimal total(PoliticaDescuento politica) {
        BigDecimal subtotal = subtotal();
        return subtotal.subtract(politica.calcular(subtotal));
    }

    public static Pedido reconstruir(Long id, Cliente cliente,
                                     List<LineaPedido> lineas, EstadoPedido estado) {
        Pedido pedido = new Pedido(cliente);
        pedido.id = id;
        pedido.lineas.addAll(lineas);
        pedido.estado = estado;
        return pedido;
    }

    private void validarModificable() {
        if (estado != EstadoPedido.CREADO) {
            throw new PedidoInvalidoException(
                    "El pedido ya no se puede modificar (estado: " + estado + ")");
        }
    }

    public void asignarId(Long id) { this.id = id; }
    public Long getId() { return id; }
    public Cliente getCliente() { return cliente; }
    public EstadoPedido getEstado() { return estado; }
    public List<LineaPedido> getLineas() { return Collections.unmodifiableList(lineas); }
}
