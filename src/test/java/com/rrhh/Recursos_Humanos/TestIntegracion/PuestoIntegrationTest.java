package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.rrhh.Recursos_Humanos.Controladores.PuestoController;
import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import com.rrhh.Recursos_Humanos.Servicios.PuestoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PuestoController.class)
class PuestoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PuestoService puestoService;

    @Test
    void testObtenerPuestos() throws Exception {
        List<Puesto> puestos = List.of(new Puesto(1L, "Analista", 500000.0));
        when(puestoService.obtenerTodos()).thenReturn(puestos);

        mockMvc.perform(get("/puestos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("Analista"))
                .andExpect(jsonPath("$[0].salario").value(500000.0));
    }

    @Test
    void testAgregarPuesto() throws Exception {
        Puesto nuevo = new Puesto(2L, "Gerente", 900000.0);
        when(puestoService.guardar(org.mockito.Mockito.any(Puesto.class))).thenReturn(nuevo);

        String json = """
            {"nombre":"Gerente","salario":900000.0}
        """;

        mockMvc.perform(post("/puestos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Gerente"))
                .andExpect(jsonPath("$.salario").value(900000.0));
    }
}
