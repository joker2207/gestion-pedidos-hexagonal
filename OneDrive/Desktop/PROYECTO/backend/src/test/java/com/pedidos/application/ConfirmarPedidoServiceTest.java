package com.pedidos.application;

import com.pedidos.application.port.out.NotificacionPort;
import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.application.service.ConfirmarPedidoService;
import com.pedidos.domain.exception.RecursoNoEncontradoException;
import com.pedidos.domain.model.Cliente;
import com.pedidos.domain.model.EstadoPedido;
import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.model.Producto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ConfirmarPedidoServiceTest {

    private PedidoRepositoryPort pedidos;
    private ProductoRepositoryPort productos;
    private NotificacionPort notificacion;
    private ConfirmarPedidoService servicio;

    @BeforeEach
    void setUp() {
        pedidos = mock(PedidoRepositoryPort.class);
        productos = mock(ProductoRepositoryPort.class);
        notificacion = mock(NotificacionPort.class);
        servicio = new ConfirmarPedidoService(pedidos, productos, notificacion);
    }

    @Test
    void confirmaPedidoGuardaYNotifica() {
        Producto teclado = new Producto(1L, "Teclado", new BigDecimal("100000"), 10);
        Pedido pedido = new Pedido(new Cliente(1L, "Ana", "ana@mail.com"));
        pedido.agregarLinea(teclado, 2);

        when(pedidos.buscarPorId(1L)).thenReturn(Optional.of(pedido));
        when(pedidos.guardar(any(Pedido.class))).thenAnswer(inv -> inv.getArgument(0));

        Pedido resultado = servicio.confirmar(1L);

        assertEquals(EstadoPedido.CONFIRMADO, resultado.getEstado());
        assertEquals(8, teclado.getStock());
        verify(productos).guardar(teclado);
        verify(notificacion).notificarPedidoConfirmado(resultado);
    }

    @Test
    void lanzaExcepcionSiElPedidoNoExiste() {
        when(pedidos.buscarPorId(99L)).thenReturn(Optional.empty());

        assertThrows(RecursoNoEncontradoException.class, () -> servicio.confirmar(99L));
    }
}
