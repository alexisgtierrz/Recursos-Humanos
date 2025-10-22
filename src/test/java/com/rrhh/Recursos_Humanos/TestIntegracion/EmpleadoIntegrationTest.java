package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Modelos.Puesto;
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


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmpleadoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Puesto puesto;

    @BeforeEach
    void setUp() {
        puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        puestoRepository.save(puesto);
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testCrearEmpleado_exitoso() throws Exception {
        Empleado empleado = new Empleado();
        empleado.setNombre("Juan");
        empleado.setApellido("Perez");
        empleado.setDni("12345678");
        empleado.setFechaNacimiento("15/08/1999");
        empleado.setEmail("juanperez@example.com");
        empleado.setTelefono("1122334455");
        empleado.setPuesto(puesto);
        empleado.setSalario(150000.0);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Juan"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testCrearEmpleado_conDniDuplicado_lanzaError() throws Exception {
        Empleado empleado1 = new Empleado();
        empleado1.setNombre("Carlos");
        empleado1.setApellido("Lopez");
        empleado1.setDni("87654321");
        empleado1.setFechaNacimiento("01/01/1995");
        empleado1.setEmail("carlos@example.com");
        empleado1.setTelefono("1100223344");
        empleado1.setPuesto(puesto);
        empleado1.setSalario(200000.0);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado1)))
                .andExpect(status().isOk());

        // Segundo empleado con el mismo DNI
        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Enzo");
        empleado2.setApellido("Fernandez");
        empleado2.setDni("87654321"); // mismo DNI
        empleado2.setFechaNacimiento("02/02/1992");
        empleado2.setEmail("enzo912@example.com");
        empleado2.setTelefono("4498765432");
        empleado2.setPuesto(puesto);
        empleado2.setSalario(180000.0);

        mockMvc.perform(post("/api/empleados")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado2)))
                .andExpect(status().isInternalServerError()); // lanza IllegalArgumentException
    }
}
