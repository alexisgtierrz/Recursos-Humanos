package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rrhh.Recursos_Humanos.Modelos.Empleado;
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


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.hasSize;
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class EmpleadoIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PuestoRepository puestoRepository;

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Puesto puesto;

    @BeforeEach
    void setUp() {
        empleadoRepository.deleteAll();
        puestoRepository.deleteAll();

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
                        .with(csrf())
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
        empleado1.setDni("12345678");
        empleado1.setFechaNacimiento("01/01/1995");
        empleado1.setEmail("carlos@example.com");
        empleado1.setTelefono("1100223344");
        empleado1.setPuesto(puesto);
        empleado1.setSalario(200000.0);

        mockMvc.perform(post("/api/empleados")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado1)))
                .andExpect(status().isOk());

        Empleado empleado2 = new Empleado();
        empleado2.setNombre("Enzo");
        empleado2.setApellido("Fernandez");
        empleado2.setDni("12345678"); // mismo DNI
        empleado2.setFechaNacimiento("02/02/1992");
        empleado2.setEmail("enzo912@example.com");
        empleado2.setTelefono("4498765432");
        empleado2.setPuesto(puesto);
        empleado2.setSalario(180000.0);

        // Verificamos que el servicio rechaza el DNI duplicado
        mockMvc.perform(post("/api/empleados")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleado2)))
                .andExpect(status().isInternalServerError());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testGetTodosLosEmpleados() throws Exception {
        Empleado empleado = new Empleado();
        empleado.setNombre("Ana");
        empleado.setApellido("Garcia");
        empleado.setDni("12345678");
        empleado.setFechaNacimiento("02/02/1992");
        empleado.setEmail("ana@example.com");
        empleado.setTelefono("3534675435");
        empleado.setPuesto(puesto);
        empleado.setSalario(120000.0);
        empleadoRepository.save(empleado);

        mockMvc.perform(get("/api/empleados"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1))) // Verifica que la lista tiene 1 elemento
                .andExpect(jsonPath("$[0].nombre", is("Ana"))); // Verifica el nombre
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testGetEmpleadoPorId_exitoso() throws Exception {
        Empleado empleado = new Empleado();
        empleado.setNombre("Luis");
        empleado.setApellido("Martinez");
        empleado.setDni("12345678");
        empleado.setFechaNacimiento("03/03/1993");
        empleado.setEmail("luis@example.com");
        empleado.setTelefono("3534657898");
        empleado.setPuesto(puesto);
        empleado.setSalario(130000.0);
        Empleado empleadoGuardado = empleadoRepository.save(empleado);
        Long idGuardado = empleadoGuardado.getId();

        mockMvc.perform(get("/api/empleados/" + idGuardado))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(idGuardado.intValue())))
                .andExpect(jsonPath("$.nombre", is("Luis")));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testGetEmpleadoPorId_noEncontrado() throws Exception {

        mockMvc.perform(get("/api/empleados/9999"))
                .andExpect(status().isNotFound());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testActualizarEmpleado_exitoso() throws Exception {
        Empleado empleadoBase = new Empleado();
        empleadoBase.setNombre("Maria");
        empleadoBase.setApellido("Gomez");
        empleadoBase.setDni("12345676");
        empleadoBase.setFechaNacimiento("04/04/1994");
        empleadoBase.setEmail("maria@example.com");
        empleadoBase.setTelefono("3534543213");
        empleadoBase.setPuesto(puesto);
        empleadoBase.setSalario(100000.0);
        Empleado empleadoGuardado = empleadoRepository.save(empleadoBase);
        Long idGuardado = empleadoGuardado.getId();

        Empleado empleadoActualizado = new Empleado();
        empleadoActualizado.setNombre("MariaActualizada");
        empleadoActualizado.setApellido("Gomez");
        empleadoActualizado.setDni("12345678");
        empleadoActualizado.setFechaNacimiento("04/04/1994");
        empleadoActualizado.setEmail("maria.actualizada@example.com"); // Email actualizado
        empleadoActualizado.setTelefono("3534123456");
        empleadoActualizado.setPuesto(puesto);
        empleadoActualizado.setSalario(110000.0); // Salario actualizado

        mockMvc.perform(put("/api/empleados/" + idGuardado)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleadoActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("MariaActualizada")))
                .andExpect(jsonPath("$.salario", is(110000.0)));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testBorrarEmpleado_exitoso() throws Exception {
        Empleado empleadoParaBorrar = new Empleado();
        empleadoParaBorrar.setNombre("Pedro");
        empleadoParaBorrar.setApellido("Sanchez");
        empleadoParaBorrar.setDni("12345678");
        empleadoParaBorrar.setFechaNacimiento("05/05/1995");
        empleadoParaBorrar.setEmail("pedro@example.com");
        empleadoParaBorrar.setTelefono("3534312345");
        empleadoParaBorrar.setPuesto(puesto);
        empleadoParaBorrar.setSalario(90000.0);
        Empleado empleadoGuardado = empleadoRepository.save(empleadoParaBorrar);
        Long idGuardado = empleadoGuardado.getId();

        mockMvc.perform(delete("/api/empleados/" + idGuardado)
                        .with(csrf()))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testCrearEmpleado_conDatosInvalidos_lanzaError400() throws Exception {
        Empleado empleadoInvalido = new Empleado();
        empleadoInvalido.setNombre("Juan");
        empleadoInvalido.setApellido(null);
        empleadoInvalido.setDni("12345678");
        empleadoInvalido.setFechaNacimiento("15/08/1999");
        empleadoInvalido.setEmail("juanperez@example.com");
        empleadoInvalido.setTelefono("3534565555");
        empleadoInvalido.setPuesto(puesto);
        empleadoInvalido.setSalario(150000.0);

        mockMvc.perform(post("/api/empleados")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(empleadoInvalido)))
                .andExpect(status().isBadRequest());
    }
}