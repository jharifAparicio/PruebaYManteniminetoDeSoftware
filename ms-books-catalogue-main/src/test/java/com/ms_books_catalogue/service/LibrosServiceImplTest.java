package com.ms_books_catalogue.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ms_books_catalogue.controller.model.LibroDto;
import com.ms_books_catalogue.data.LibroRepository;
import com.ms_books_catalogue.data.model.Libro;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class LibrosServiceImplTest {

    private LibroRepository repository;
    private ObjectMapper objectMapper;
    private LibrosServiceImpl service;

    @BeforeEach
    void setUp() {
        repository = mock(LibroRepository.class);
        objectMapper = new ObjectMapper();
        service = new LibrosServiceImpl(repository, objectMapper);
    }

    @DisplayName("libros sin filtros con lista vacía")
    @Test
    void testGetLibrosVacio() {
        when(repository.getLibros()).thenReturn(Collections.emptyList());

        List<Libro> result = service.getLibros(null, null, null, null, null, null, null);

        assertThat(result).isNull();
    }

    @DisplayName("getLibro encontrado")
    @Test
    void testGetLibroEncontrado() {
        Libro libro = Libro.builder().id(1L).titulo("Java").build();
        when(repository.getById(1L)).thenReturn(libro);

        Libro result = service.getLibro("1");

        assertThat(result).isNotNull();
        assertThat(result.getTitulo()).isEqualTo("Java");
    }

    @DisplayName("getLibro no encontrado")
    @Test
    void testGetLibroNoEncontrado() {
        when(repository.getById(1L)).thenReturn(null);

        Libro result = service.getLibro("1");

        assertThat(result).isNull();
    }

    @DisplayName("crear libro dto nulo")
    @Test
    void testCrearLibroDtoNulo() {
        Libro result = service.crearLibro(null);

        assertThat(result).isNull();
        verify(repository, never()).save(any());
    }

    @DisplayName("crear libro dto inválido")
    @Test
    void testCrearLibroDatosInvalidos() {
        LibroDto dto = new LibroDto();
        dto.setTitulo("");   // vacío en vez de null
        dto.setAutor("");
        dto.setEditorial("");
        dto.setGenero("");
        dto.setPrecio(null);
        dto.setStock(null);
        dto.setVisible(null);

        Libro result = service.crearLibro(dto);

        assertThat(result).isNull();
        verify(repository, never()).save(any());
    }

    @DisplayName("actualizar libro con JSON válido")
    @Test
    void testActualizarLibroJsonValido() {
        Libro libro = Libro.builder().id(1L).titulo("Viejo").build();
        when(repository.getById(1L)).thenReturn(libro);
        when(repository.save(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));

        String patchJson = "{\"titulo\":\"Nuevo\"}";

        Libro result = service.actualizarLibro("1", patchJson);

        assertThat(result).isNotNull();
        assertThat(result.getTitulo()).isEqualTo("Nuevo");
        verify(repository).save(any(Libro.class));
    }

    @DisplayName("actualizar libro con JSON inválido")
    @Test
    void testActualizarLibroJsonInvalido() {
        Libro libro = Libro.builder().id(1L).titulo("Viejo").build();
        when(repository.getById(1L)).thenReturn(libro);

        String badJson = "{titulo:}"; // inválido

        Libro result = service.actualizarLibro("1", badJson);

        assertThat(result).isNull();
        verify(repository, never()).save(any());
    }

    @DisplayName("actualizar libro con JSON libro no encontrado")
    @Test
    void testActualizarLibroJsonLibroNoEncontrado() {
        when(repository.getById(1L)).thenReturn(null);

        Libro result = service.actualizarLibro("1", "{\"titulo\":\"Nuevo\"}");

        assertThat(result).isNull();
        verify(repository, never()).save(any());
    }

    @DisplayName("actualizar libro con DTO")
    @Test
    void testActualizarLibroConDto() {
        Libro libro = Libro.builder().id(1L).titulo("Viejo").build();
        when(repository.getById(1L)).thenReturn(libro);
        when(repository.save(any(Libro.class))).thenAnswer(inv -> inv.getArgument(0));

        LibroDto dto = new LibroDto();
        dto.setTitulo("Nuevo");

        Libro result = service.actualizarLibro("1", dto);

        assertThat(result).isNotNull();
        assertThat(result.getTitulo()).isEqualTo("Nuevo");
        verify(repository).save(libro);
    }

    @DisplayName("actualizar libro con DTO libro no encontrado")
    @Test
    void testActualizarLibroConDtoNoEncontrado() {
        when(repository.getById(1L)).thenReturn(null);

        Libro result = service.actualizarLibro("1", new LibroDto());

        assertThat(result).isNull();
        verify(repository, never()).save(any());
    }
}
