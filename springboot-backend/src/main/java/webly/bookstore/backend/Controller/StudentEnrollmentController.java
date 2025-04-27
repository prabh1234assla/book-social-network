package webly.bookstore.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.fge.jsonpatch.JsonPatch;

import webly.bookstore.backend.Models.StudentEnrollment;
import webly.bookstore.backend.Models.BaseModel.StudentEnrollmentModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.Utils.UserRole;
import webly.bookstore.backend.Service.StudentEnrollmentService;

import java.util.List;

@RestController
@RequestMapping("/enrollment")
public class StudentEnrollmentController {

    private final StudentEnrollmentService service;

    public StudentEnrollmentController(StudentEnrollmentService service) {
        this.service = service;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PostMapping("/")
    public ResponseEntity<StudentEnrollment> createEnrollment(@RequestBody StudentEnrollmentModel enrollment) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to enroll students!");
        }
        return new ResponseEntity<>(service.create(enrollment), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<StudentEnrollment>> getAllEnrollments() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEnrollment> getOneEnrollment(@PathVariable("id") int id) throws Exception {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneEnrollment(@PathVariable("id") int id, @RequestBody StudentEnrollmentModel enrollment){
        service.updateStudentEnrollmentById(id, enrollment);
    }

    @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<StudentEnrollment> patchOneEnrollment(@PathVariable("id") int id, @RequestBody JsonPatch patch){
        return new ResponseEntity<>(service.patchOne(id, patch), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneEnrollment(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete enrollment!");
        }
        service.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllEnrollments() throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete all enrollments!");
        }
        service.deleteAll();
    }
}
