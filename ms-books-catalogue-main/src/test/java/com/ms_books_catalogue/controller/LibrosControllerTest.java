package com.ms_books_catalogue.controller;

import com.ms_books_catalogue.controller.model.LibroDto;
import com.ms_books_catalogue.data.model.Libro;
import com.ms_books_catalogue.service.LibrosService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibrosController.class)
class LibrosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LibrosService service;

    @Test
    void testGetLibros() throws Exception {
        Libro libro = Libro.builder().id(1L).titulo("Spring").build();
        when(service.getLibros(null, null, null, null, null, null, null))
                .thenReturn(List.of(libro));

        mockMvc.perform(get("/libros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Spring"));
    }

    @Test
    void testGetLibroPorIdEncontrado() throws Exception {
        Libro libro = Libro.builder().id(1L).titulo("Java").build();
        when(service.getLibro("1")).thenReturn(libro);

        mockMvc.perform(get("/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Java"));
    }

    @Test
    void testGetLibroPorIdNoEncontrado() throws Exception {
        when(service.getLibro("2")).thenReturn(null);

        mockMvc.perform(get("/libros/2"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddLibro() throws Exception {
        LibroDto dto = new LibroDto();
        dto.setTitulo("Nuevo libro");
        dto.setAutor("Autor");
        dto.setEditorial("Editorial");
        dto.setGenero("Genero");
        dto.setPrecio(100.0F);
        dto.setStock(5);
        dto.setVisible(true);

        Libro libro = Libro.builder().id(1L).titulo("Nuevo libro").build();
        when(service.crearLibro(any(LibroDto.class))).thenReturn(libro);

        mockMvc.perform(post("/libros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                        {"titulo":"Nuevo libro","autor":"Autor","editorial":"Editorial","genero":"Genero","precio":100.0,"stock":5,"visible":true}
                        """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Nuevo libro"));
    }
}
