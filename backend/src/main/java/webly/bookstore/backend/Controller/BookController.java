package webly.bookstore.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webly.bookstore.backend.Book.Book;
import webly.bookstore.backend.Book.BookModel;
import webly.bookstore.backend.Service.BookService;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService service;

    public BookController(BookService service){
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody BookModel book){
        return new ResponseEntity<>(service.create(book), HttpStatus.CREATED);
    }
}
