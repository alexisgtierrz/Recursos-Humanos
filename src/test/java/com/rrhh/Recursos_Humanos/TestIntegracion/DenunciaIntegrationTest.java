package com.rrhh.Recursos_Humanos.TestIntegracion;

import com.rrhh.Recursos_Humanos.Controladores.DenunciaController;
import com.rrhh.Recursos_Humanos.Modelos.Denuncia;
import com.rrhh.Recursos_Humanos.Servicios.DenunciaService;
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

@WebMvcTest(DenunciaController.class)
class DenunciaIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DenunciaService denunciaService;

    @Test
    void testListarDenuncias() throws Exception {
        List<Denuncia> denuncias = List.of(new Denuncia(1L, "Acoso laboral", "En investigación"));
        when(denunciaService.obtenerTodas()).thenReturn(denuncias);

        mockMvc.perform(get("/denuncias"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].descripcion").value("Acoso laboral"))
                .andExpect(jsonPath("$[0].estado").value("En investigación"));
    }

    @Test
    void testCrearDenuncia() throws Exception {
        Denuncia nueva = new Denuncia(2L, "Discriminación", "Pendiente");
        when(denunciaService.guardar(org.mockito.Mockito.any(Denuncia.class))).thenReturn(nueva);

        String json = """
            {"descripcion":"Discriminación","estado":"Pendiente"}
        """;

        mockMvc.perform(post("/denuncias")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.descripcion").value("Discriminación"))
                .andExpect(jsonPath("$.estado").value("Pendiente"));
    }
}
 
