package com.com470.bookApi.controller;

import com.com470.bookApi.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import com.com470.bookApi.repository.BookRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

class BookControllerTest {
    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookController bookController;

    @BeforeEach
    void setUp() {
        initMocks(this);
    }

    @DisplayName("Lista de libros")
    @Test
    void retriveAllBooks() {
        Book book = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<Book> books = bookController.retrieveAllBooks();
        assertNotNull(books);
        assertFalse(books.isEmpty());
        assertEquals(books.size(), books.size());
    }

    @Test
    void retrieveBook(){
        Book book = new Book();
        when(bookRepository.findById(1)).thenReturn(Optional.of(book));
        Book book1 = bookController.retrieveBook(1).getBody();
        assertNotNull(book1);
        assertFalse(false);
        assertEquals(book, book1);
    }

    @Test
    void createBook(){
        Book book = new Book();
        book.setId(3);
        when(bookRepository.existsById(3)).thenReturn(false);
        when(bookRepository.save(book)).thenReturn(book);

        ResponseEntity<Object> response = bookController.createBook(book);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(book, response.getBody());
        verify(bookRepository).save(book);
    }

    @Test
    void deleteBook(){
        Book book = new Book();
        book.setId(1);

        when(bookRepository.findById(1)).thenReturn(Optional.of(book));

        ResponseEntity<Object> response = bookController.deleteBook(1);

        assertEquals(200, response.getStatusCode().value());
        verify(bookRepository).delete(book);
    }
}