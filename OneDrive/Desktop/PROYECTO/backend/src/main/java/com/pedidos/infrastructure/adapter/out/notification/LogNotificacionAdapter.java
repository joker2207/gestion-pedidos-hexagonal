package com.pedidos.infrastructure.adapter.out.notification;

import com.pedidos.application.port.out.NotificacionPort;
import com.pedidos.domain.model.Pedido;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Adaptador de notificaciones que escribe en el log.
 * Se puede reemplazar por uno de correo real sin tocar la aplicacion ni el dominio.
 */
@Component
public class LogNotificacionAdapter implements NotificacionPort {

    private static final Logger log = LoggerFactory.getLogger(LogNotificacionAdapter.class);

    @Override
    public void notificarPedidoConfirmado(Pedido pedido) {
        log.info("Correo a {}: tu pedido #{} fue confirmado",
                pedido.getCliente().getEmail(), pedido.getId());
    }
}
