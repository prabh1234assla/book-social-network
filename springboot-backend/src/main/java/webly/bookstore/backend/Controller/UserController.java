package webly.bookstore.backend.Controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

import com.github.fge.jsonpatch.JsonPatch;

import webly.bookstore.backend.Models.Fee;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.BaseModel.UserModel;
import webly.bookstore.backend.Models.Utils.UserRole;
import webly.bookstore.backend.Service.CourseService;
import webly.bookstore.backend.Service.FeeService;
import webly.bookstore.backend.Service.MarksService;
import webly.bookstore.backend.Service.StudentEnrollmentService;
import webly.bookstore.backend.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final CourseService courseService;
    private final MarksService marksService;
    private final FeeService feeService; 
    private final StudentEnrollmentService studentEnrollmentService;

    public UserController(UserService service, CourseService courseService, MarksService marksService, FeeService feeService, StudentEnrollmentService studentEnrollmentService) {
        this.service = service;
        this.courseService = courseService;
        this.marksService = marksService;
        this.feeService = feeService;
        this.studentEnrollmentService = studentEnrollmentService;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    // get yourself
    @GetMapping("/me")
    public ResponseEntity<User> authenticatedUser() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(currentUser);
    }

    // // add a fee
    // @PostMapping("/fee/{id}")
    // public ResponseEntity<Fee> addFee(@PathVariable("id") int id) throws Exception {
    //     User currentUser = getAuthenticatedUser();

    //     if (currentUser.getRole() != UserRole.ADMIN) {
    //         throw new Exception("Gain admin priveleges to create fee tickets!!!");
    //     }

    //     Book addedBook = bookService.findById(id);
    //     addedBook.setBorrowed(true);

    //     Set<Book> listOfBooks = currentUser.getBooks();
    //     listOfBooks.add(addedBook);

    //     service.updateOne(currentUser.getId(), currentUser);

    //     return ResponseEntity.ok(addedBook);
    // }

    // @PostMapping("/book/{id}")
    // public ResponseEntity<Book> addBook(@PathVariable("id") int id) throws Exception {
    //     User currentUser = getAuthenticatedUser();

    //     if (currentUser.getRole() == UserRole.ADMIN) {
    //         throw new Exception("Gain user priveleges to borrow a book!!!");
    //     }

    //     Book addedBook = bookService.findById(id);
    //     addedBook.setBorrowed(true);

    //     Set<Book> listOfBooks = currentUser.getBooks();
    //     listOfBooks.add(addedBook);

    //     service.updateOne(currentUser.getId(), currentUser);

    //     return ResponseEntity.ok(addedBook);
    // }

}
