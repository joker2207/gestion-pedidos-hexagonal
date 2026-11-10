package com.pedidos.domain;

import com.pedidos.domain.exception.PedidoInvalidoException;
import com.pedidos.domain.exception.StockInsuficienteException;
import com.pedidos.domain.model.Cliente;
import com.pedidos.domain.model.EstadoPedido;
import com.pedidos.domain.model.Pedido;
import com.pedidos.domain.model.Producto;
import com.pedidos.domain.service.DescuentoPorMonto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PedidoTest {

    private Cliente cliente;
    private Producto teclado;

    @BeforeEach
    void setUp() {
        cliente = new Cliente(1L, "Ana", "ana@mail.com");
        teclado = new Producto(1L, "Teclado", new BigDecimal("100000"), 10);
    }

    @Test
    void confirmarPedidoDescuentaStock() {
        Pedido pedido = new Pedido(cliente);
        pedido.agregarLinea(teclado, 3);

        pedido.confirmar();

        assertEquals(EstadoPedido.CONFIRMADO, pedido.getEstado());
        assertEquals(7, teclado.getStock());
    }

    @Test
    void noPermiteAgregarMasCantidadQueElStock() {
        Pedido pedido = new Pedido(cliente);

        assertThrows(StockInsuficienteException.class,
                () -> pedido.agregarLinea(teclado, 11));
    }

    @Test
    void noPermiteConfirmarPedidoVacio() {
        Pedido pedido = new Pedido(cliente);

        assertThrows(PedidoInvalidoException.class, pedido::confirmar);
    }

    @Test
    void cancelarPedidoConfirmadoRepoeElStock() {
        Pedido pedido = new Pedido(cliente);
        pedido.agregarLinea(teclado, 3);
        pedido.confirmar();

        pedido.cancelar();

        assertEquals(EstadoPedido.CANCELADO, pedido.getEstado());
        assertEquals(10, teclado.getStock());
    }

    @Test
    void aplicaDescuentoCuandoElTotalSuperaElUmbral() {
        Pedido pedido = new Pedido(cliente);
        pedido.agregarLinea(teclado, 6); // 600.000

        BigDecimal total = pedido.total(new DescuentoPorMonto());

        assertEquals(0, new BigDecimal("540000").compareTo(total));
    }

    @Test
    void noAplicaDescuentoCuandoElTotalNoSuperaElUmbral() {
        Pedido pedido = new Pedido(cliente);
        pedido.agregarLinea(teclado, 2); // 200.000

        BigDecimal total = pedido.total(new DescuentoPorMonto());

        assertEquals(0, new BigDecimal("200000").compareTo(total));
    }
}
