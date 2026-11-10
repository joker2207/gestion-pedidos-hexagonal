package com.pedidos.infrastructure.config;

import com.pedidos.application.port.in.GestionarClientesUseCase;
import com.pedidos.application.port.in.GestionarProductosUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

/**
 * Carga datos de prueba al arrancar si la base de datos esta vacia.
 */
@Configuration
public class DatosDeEjemplo {

    @Bean
    CommandLineRunner cargarDatos(GestionarClientesUseCase clientes,
                                  GestionarProductosUseCase productos) {
        return args -> {
            if (!clientes.listar().isEmpty()) {
                return;
            }
            clientes.crear("Ana Gomez", "ana@mail.com");
            clientes.crear("Carlos Perez", "carlos@mail.com");

            productos.crear("Teclado mecanico", new BigDecimal("150000"), 20);
            productos.crear("Mouse inalambrico", new BigDecimal("80000"), 35);
            productos.crear("Monitor 27 pulgadas", new BigDecimal("900000"), 8);
        };
    }
}
