package webly.campusSphere.backend.Controller;

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

import webly.campusSphere.backend.DTOs.CourseServiceDTOs.CourseDetails;
import webly.campusSphere.backend.DTOs.FeeServiceDTOs.FeeDetails;
import webly.campusSphere.backend.DTOs.MarksServiceDTOs.MarksDetails;
import webly.campusSphere.backend.DTOs.StudentEnrollmentServiceDTOs.StudentEnrollmentDetails;
import webly.campusSphere.backend.DTOs.UserServiceDTOs.UserDetails;
import webly.campusSphere.backend.Models.Fee;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.UserModel;
import webly.campusSphere.backend.Models.Utils.UserRole;
import webly.campusSphere.backend.Service.CourseService;
import webly.campusSphere.backend.Service.FeeService;
import webly.campusSphere.backend.Service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;
    private final CourseService courseService;
    private final FeeService feeService; 

    public UserController(UserService service, CourseService courseService, FeeService feeService) {
        this.service = service;
        this.courseService = courseService;
        this.feeService = feeService;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return (User) authentication.getPrincipal();
    }

    // get yourself
    @GetMapping(value = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDetails> authenticatedUser() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(UserDetails.generaDto(currentUser));
    }

    // get all courses under you
    @GetMapping(value = "/me/courseStudied", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseDetails>> listAllCoursesStudied() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(service.findAllCoursesAssociated(currentUser.getId()));
    }

    // get all courses taught by me
    @GetMapping(value = "/me/courseTaught", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<CourseDetails>> listAllCoursesTaught() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(courseService.findAllByFacultyId(currentUser.getId()));
    }

    // get all fees
    @GetMapping(value = "/me/fees", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<FeeDetails>> listAllFees() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(service.findAllFees(currentUser.getId()));
    }

    // get all enrollments
    @GetMapping(value = "/me/enrollments", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<StudentEnrollmentDetails>> listAllEnrollments() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(service.findAllEnrollments(currentUser.getId()));
    }

    // get all marks
    @GetMapping(value = "/me/marks", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<MarksDetails>> listMarks() {
        User currentUser = getAuthenticatedUser();

        return ResponseEntity.ok(service.findAllMarks(currentUser.getId()));
    }

    @GetMapping()
    public ResponseEntity<List<UserDetails>> getAllUsers() throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to see all users!");
        }

        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/students")
    public ResponseEntity<List<UserDetails>> getAllStudents() throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() == UserRole.STUDENT) {
            throw new Exception("Gain admin or faculty privileges to see all students!");
        }

        return new ResponseEntity<>(service.findAllStudents(), HttpStatus.OK);
    }

    @GetMapping("/faculty")
    public ResponseEntity<List<UserDetails>> getAllFaculty() throws Exception {
        return new ResponseEntity<>(service.findAllFaculty(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserById(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete user!");
        }

        UserDetails userToBeDeleted = service.findById(id);

        if (userToBeDeleted.getRole() == UserRole.ADMIN.toString()) {
            throw new Exception("Admins can't be Deleted Sorry!!!");
        }

        service.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers() throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete all users!");
        }

        List<UserDetails> userDetails = service.findAll();

        for (UserDetails userDetail: userDetails){
            if (userDetail.getRole() == UserRole.ADMIN.toString()) {
                System.out.println("Admins can't be Deleted Sorry!!!");
            }
            else {
                service.deleteById(userDetail.getId());
            }
        }

    }

    // @GetMapping(value = "/me/fees", produces = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Set<Fee>> listAllFees() throws Exception {
    //     User currentUser = getAuthenticatedUser();

    //     if (currentUser.getRole() != UserRole.STUDENT) {
    //         throw new Exception("Only students have fee tickets assigned under them!");
    //     }

    //     Set<Fee> listOfFees = currentUser.getFees();

    //     return ResponseEntity.ok(listOfFees);
    // }

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
