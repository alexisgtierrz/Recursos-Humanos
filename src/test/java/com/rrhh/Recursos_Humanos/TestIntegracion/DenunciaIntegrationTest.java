package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
import com.rrhh.Recursos_Humanos.Modelos.Puesto;
import com.rrhh.Recursos_Humanos.Repositorios.DenunciaRepository;
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
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DenunciaIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private PuestoRepository puestoRepository;
    @Autowired private EmpleadoRepository empleadoRepository;
    @Autowired private DenunciaRepository denunciaRepository;
    @Autowired private ObjectMapper objectMapper;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        denunciaRepository.deleteAll();
        empleadoRepository.deleteAll();
        puestoRepository.deleteAll();

        Puesto puesto = new Puesto();
        puesto.setNombre("Desarrollador");
        puesto = puestoRepository.save(puesto);

        empleado = new Empleado();
        empleado.setNombre("María");
        empleado.setApellido("Gómez");
        empleado.setFechaNacimiento("15/08/1999");
        empleado.setDni("87654321");
        empleado.setEmail("maria@example.com");
        empleado.setTelefono("1123456789");
        empleado.setPuesto(puesto);
        empleado.setSalario(100000.0);
        empleado = empleadoRepository.save(empleado);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testCrearDenuncia_exitoso() throws Exception {
        Denuncia d = new Denuncia();
        d.setDescripcion("Falta a horario");

        mockMvc.perform(post("/api/denuncias/empleado/" + empleado.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(d)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Falta a horario"))
                .andExpect(jsonPath("$.id").exists());
    }

    @WithMockUser
    @Test
    void testListarDenuncias() throws Exception {
        Denuncia denuncia = new Denuncia();
        denuncia.setDescripcion("Incumplimiento horario");
        denuncia.setFecha(LocalDateTime.now());
        denuncia.setEmpleado(empleado);
        denunciaRepository.save(denuncia);

        mockMvc.perform(get("/api/denuncias")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Incumplimiento horario"));
    }
}
