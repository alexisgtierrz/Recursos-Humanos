package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import com.rrhh.Recursos_Humanos.Repositorios.EmpleadoRepository;
import com.rrhh.Recursos_Humanos.Repositorios.PuestoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class PuestoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        empleadoRepository.deleteAll();
        puestoRepository.deleteAll();
    }

    @WithMockUser // cualquier usuario autenticado (la seguridad exige autenticación)
    @Test
    void testObtenerPuestos() throws Exception {
        Puesto p1 = new Puesto();
        p1.setNombre("Desarrollador");
        p1.setDescripcion("Backend");
        Puesto p2 = new Puesto();
        p2.setNombre("Analista");
        p2.setDescripcion("Funcional");

        puestoRepository.saveAll(List.of(p1, p2));

        mockMvc.perform(get("/api/puestos").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[?(@.nombre=='Desarrollador')].descripcion", hasItem("Backend")))
                .andExpect(jsonPath("$[?(@.nombre=='Analista')].descripcion", hasItem("Funcional")));
    }

    @WithMockUser
    @Test
    void testAgregarPuesto() throws Exception {
        Puesto nuevo = new Puesto();
        nuevo.setNombre("DevOps");
        nuevo.setDescripcion("CI/CD");

        mockMvc.perform(post("/api/puestos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(nuevo)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.nombre").value("DevOps"))
                .andExpect(jsonPath("$.descripcion").value("CI/CD"));
    }
}