package com.com470.bookApi.controller;

import com.com470.bookApi.model.Book;
import com.com470.bookApi.model.Review;
import com.com470.bookApi.repository.BookRepository;
import com.com470.bookApi.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ReviewController {
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @GetMapping(path = "/books/{id}/reviews")
    public ResponseEntity<List<Review>> retrieveAllReviews(@PathVariable int id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return ResponseEntity.ok(book.get().getReviews());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/books/{id}/reviews")
    public ResponseEntity<Object> createReview(@PathVariable int id, @RequestBody Review review) {
        Optional<Book> bookOptional = bookRepository.findById(id);

        if (!bookOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        Book book = bookOptional.get();
        review.setBook(book);

        return ResponseEntity.ok(reviewRepository.save(review));
    }
}
