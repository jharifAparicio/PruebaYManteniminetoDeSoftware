package com.com470.bookApi.controller;

import com.com470.bookApi.model.Book;
import com.com470.bookApi.model.Review;
import com.com470.bookApi.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class BookControllerTest {
    private  static int ID=2;
    private static String AUTHOR="Homero";
    private static String TITLE="La Odisea";
    private static  int RELEASE;
    private static List<Review> REVIEW_LIST=new ArrayList<>();
    private static final Book BOOK = new Book();
    private static final Optional<Book> OPTIONAL_BOOK= Optional.of(BOOK);
    private static final Optional<Book> OPTIONAL_BOOK_EMPTY= Optional.empty();

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookController bookController;

    @BeforeEach
    void setUp() {
        final Book book = new Book();
        BOOK.setAuthor(AUTHOR);
        BOOK.setId(ID);
        BOOK.setRelease(RELEASE);
        BOOK.setReviews(REVIEW_LIST);
        BOOK.setTitle(TITLE);
    }

    @Test
    @DisplayName("lista de libros")
    void retrieveAllBooks() {
        final Book book = new Book();
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book));
        List<Book> response= bookController.retrieveAllBooks();
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertEquals(response.size(),1);
    }

    @Test
    @DisplayName("devuelve libro por ID")
    void retrieveBookOK() {
        when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
        ResponseEntity<Book>response=bookController.retrieveBook(ID);
        assertEquals(response.getBody().getAuthor(),AUTHOR);
        assertEquals(response.getBody().getTitle(),TITLE);
    }

    @Test
    @DisplayName("no existe el librobuscando or ID")
    void retrieveBookNotFound() {
        when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
        ResponseEntity<Book>response=bookController.retrieveBook(ID);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

    }

    @Test
    @DisplayName("crear un registro cuando no existe registro")
    void createBook() {
        when(bookRepository.existsById(BOOK.getId())).thenReturn(Boolean.FALSE);
        ResponseEntity<Object>response=bookController.createBook(BOOK);
        assertEquals(response.getStatusCode(),HttpStatus.OK);
    }
    @Test
    @DisplayName("no crea un registro cuando ya existe")
    void createBookExist() {
        when(bookRepository.existsById(BOOK.getId())).thenReturn(Boolean.TRUE);
        ResponseEntity<Object>response=bookController.createBook(BOOK);
        assertEquals(response.getStatusCode(),HttpStatus.CONFLICT);
    }


    @Test
    @DisplayName("Elimina un registro presente")
    void deleteBookOK() {
        when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK);
        bookController.deleteBook(ID);
    }

    @Test
    @DisplayName("No Elimina registro debido a no esta presente")
    void deleteBookNotFound() {
        when(bookRepository.findById(ID)).thenReturn(OPTIONAL_BOOK_EMPTY);
        ResponseEntity<Object> response= bookController.deleteBook(ID);
        assertEquals(response.getStatusCode(),HttpStatus.NOT_FOUND);
    }
}