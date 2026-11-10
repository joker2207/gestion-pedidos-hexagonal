package com.pedidos.infrastructure.adapter.in.web;

import com.pedidos.application.port.in.CrearPedidoUseCase.CrearPedidoCommand;
import com.pedidos.application.port.in.CrearPedidoUseCase.ItemCommand;
import com.pedidos.domain.model.LineaPedido;
import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.service.PoliticaDescuento;
import com.pedidos.infrastructure.adapter.in.web.dto.PedidoRequest;
import com.pedidos.infrastructure.adapter.in.web.dto.PedidoResponse;
import com.pedidos.infrastructure.adapter.in.web.dto.PedidoResponse.LineaResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PedidoWebMapper {

    private final PoliticaDescuento politicaDescuento;

    public PedidoWebMapper(PoliticaDescuento politicaDescuento) {
        this.politicaDescuento = politicaDescuento;
    }

    public CrearPedidoCommand toCommand(PedidoRequest request) {
        List<ItemCommand> items = request.items().stream()
                .map(item -> new ItemCommand(item.productoId(), item.cantidad()))
                .toList();
        return new CrearPedidoCommand(request.clienteId(), items);
    }

    public PedidoResponse toResponse(Pedido pedido) {
        BigDecimal subtotal = pedido.subtotal();
        BigDecimal descuento = politicaDescuento.calcular(subtotal);
        List<LineaResponse> lineas = pedido.getLineas().stream().map(this::toLinea).toList();

        return new PedidoResponse(
                pedido.getId(),
                pedido.getCliente().getId(),
                pedido.getCliente().getNombre(),
                pedido.getEstado().name(),
                lineas,
                subtotal,
                descuento,
                pedido.total(politicaDescuento));
    }

    private LineaResponse toLinea(LineaPedido linea) {
        return new LineaResponse(
                linea.getProducto().getId(),
                linea.getProducto().getNombre(),
                linea.getProducto().getPrecio(),
                linea.getCantidad(),
                linea.subtotal());
    }
}
