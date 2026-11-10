package com.pedidos.infrastructure;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PedidoApiIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void flujoCompletoCrearConfirmarYCancelarPedido() throws Exception {
        long clienteId = crear("/api/clientes", "{\"nombre\":\"Test\",\"email\":\"test@mail.com\"}");
        long productoId = crear("/api/productos", "{\"nombre\":\"Mouse\",\"precio\":600000,\"stock\":5}");
        long pedidoId = crear("/api/pedidos", pedido(clienteId, productoId, 1));

        mvc.perform(post("/api/pedidos/" + pedidoId + "/confirmar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CONFIRMADO"))
                .andExpect(jsonPath("$.total").value(540000.0));

        mvc.perform(post("/api/pedidos/" + pedidoId + "/cancelar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("CANCELADO"));
    }

    @Test
    void rechazaPedidoConCantidadMayorAlStock() throws Exception {
        long clienteId = crear("/api/clientes", "{\"nombre\":\"Test2\",\"email\":\"test2@mail.com\"}");
        long productoId = crear("/api/productos", "{\"nombre\":\"Cable\",\"precio\":10000,\"stock\":2}");

        mvc.perform(post("/api/pedidos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(pedido(clienteId, productoId, 99)))
                .andExpect(status().isConflict());
    }

    private String pedido(long clienteId, long productoId, int cantidad) {
        return "{\"clienteId\":" + clienteId + ",\"items\":[{\"productoId\":" + productoId
                + ",\"cantidad\":" + cantidad + "}]}";
    }

    private long crear(String ruta, String json) throws Exception {
        String respuesta = mvc.perform(post(ruta)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        return ((Number) JsonPath.read(respuesta, "$.id")).longValue();
    }
}
