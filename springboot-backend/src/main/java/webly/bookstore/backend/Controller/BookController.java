package webly.bookstore.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.fge.jsonpatch.JsonPatch;

import webly.bookstore.backend.Models.Book;
import webly.bookstore.backend.Models.BookModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.UserRole;
import webly.bookstore.backend.Service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    // create book
    @PostMapping("/")
    public ResponseEntity<Book> createBook(@RequestBody BookModel book) throws Exception {
        User currentUser = getAuthenticatedUser();
        
        if (currentUser.getRole() == UserRole.USER) {
            throw new Exception("Gain admin priveleges to add a book!!!");
        }
        
        return new ResponseEntity<>(service.create(book), HttpStatus.CREATED);
    }

    // get all books
    // no use of authentication as both ADMINS and USERS can see the books
    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        // User currentUser = getAuthenticatedUser();

        // if (currentUser.getRole() == UserRole.USER) {
        //     throw new Exception("Gain admin priveleges to see all books!!!");
        // }

        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    // get book with specific id
    @GetMapping("/{id}")
    public ResponseEntity<Book> getOneBook(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == UserRole.USER) {
            throw new Exception("Gain admin priveleges to see a book!!!");
        }

        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    // update a book with specific id
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneBook(@PathVariable("id") int id, @RequestBody BookModel book) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == UserRole.USER) {
            throw new Exception("Gain admin priveleges to update a book!!!");
        }

        service.updateOne(id, book);
    }

    // update specific info of book with a specific id
    @PatchMapping(value = "/borrow/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> patchOneBook(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == UserRole.ADMIN) {
            throw new Exception("Gain user priveleges to borrow a book!!!");
        }

        return new ResponseEntity<>(service.patchOne(id, patch), HttpStatus.OK);
    }

    // delete book with specific id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneBook(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == UserRole.USER) {
            throw new Exception("Gain admin priveleges to delete a book!!!");
        }

        service.deleteById(id);
    }

    // delete all books
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllBooks() throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == UserRole.USER) {
            throw new Exception("Gain admin priveleges to delete all books!!!");
        }

        service.deleteAll();
    }

}
