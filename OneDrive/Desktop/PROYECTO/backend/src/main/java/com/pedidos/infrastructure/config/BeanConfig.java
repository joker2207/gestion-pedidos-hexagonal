package com.pedidos.infrastructure.config;

import com.pedidos.application.port.in.CancelarPedidoUseCase;
import com.pedidos.application.port.in.ConfirmarPedidoUseCase;
import com.pedidos.application.port.in.ConsultarPedidosUseCase;
import com.pedidos.application.port.in.CrearPedidoUseCase;
import com.pedidos.application.port.in.GestionarClientesUseCase;
import com.pedidos.application.port.in.GestionarProductosUseCase;
import com.pedidos.application.port.out.ClienteRepositoryPort;
import com.pedidos.application.port.out.NotificacionPort;
import com.pedidos.application.port.out.PedidoRepositoryPort;
import com.pedidos.application.port.out.ProductoRepositoryPort;
import com.pedidos.application.service.CancelarPedidoService;
import com.pedidos.application.service.ConfirmarPedidoService;
import com.pedidos.application.service.ConsultarPedidosService;
import com.pedidos.application.service.CrearPedidoService;
import com.pedidos.application.service.GestionarClientesService;
import com.pedidos.application.service.GestionarProductosService;
import com.pedidos.domain.service.DescuentoPorMonto;
import com.pedidos.domain.service.PoliticaDescuento;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Unico lugar donde se conectan los casos de uso con sus puertos de salida.
 */
@Configuration
public class BeanConfig {

    @Bean
    PoliticaDescuento politicaDescuento() {
        return new DescuentoPorMonto();
    }

    @Bean
    CrearPedidoUseCase crearPedidoUseCase(PedidoRepositoryPort pedidos,
                                          ClienteRepositoryPort clientes,
                                          ProductoRepositoryPort productos,
                                          TransaccionFactory tx) {
        return tx.envolver(CrearPedidoUseCase.class,
                new CrearPedidoService(pedidos, clientes, productos));
    }

    @Bean
    ConfirmarPedidoUseCase confirmarPedidoUseCase(PedidoRepositoryPort pedidos,
                                                  ProductoRepositoryPort productos,
                                                  NotificacionPort notificacion,
                                                  TransaccionFactory tx) {
        return tx.envolver(ConfirmarPedidoUseCase.class,
                new ConfirmarPedidoService(pedidos, productos, notificacion));
    }

    @Bean
    CancelarPedidoUseCase cancelarPedidoUseCase(PedidoRepositoryPort pedidos,
                                                ProductoRepositoryPort productos,
                                                TransaccionFactory tx) {
        return tx.envolver(CancelarPedidoUseCase.class,
                new CancelarPedidoService(pedidos, productos));
    }

    @Bean
    ConsultarPedidosUseCase consultarPedidosUseCase(PedidoRepositoryPort pedidos,
                                                    TransaccionFactory tx) {
        return tx.envolver(ConsultarPedidosUseCase.class, new ConsultarPedidosService(pedidos));
    }

    @Bean
    GestionarClientesUseCase gestionarClientesUseCase(ClienteRepositoryPort clientes,
                                                      TransaccionFactory tx) {
        return tx.envolver(GestionarClientesUseCase.class, new GestionarClientesService(clientes));
    }

    @Bean
    GestionarProductosUseCase gestionarProductosUseCase(ProductoRepositoryPort productos,
                                                        TransaccionFactory tx) {
        return tx.envolver(GestionarProductosUseCase.class, new GestionarProductosService(productos));
    }
}
