package webly.campusSphere.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.fge.jsonpatch.JsonPatch;

import webly.campusSphere.backend.DTOs.StudentEnrollmentServiceDTOs.StudentEnrollmentDetails;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin.enrollmentsDTO;
import webly.campusSphere.backend.Models.StudentEnrollment;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.StudentEnrollmentModel;
import webly.campusSphere.backend.Models.Utils.UserRole;
import webly.campusSphere.backend.Service.StudentEnrollmentService;

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
    public ResponseEntity<StudentEnrollmentDetails> createEnrollment(@RequestBody StudentEnrollmentModel enrollment) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to enroll students!");
        }
        System.out.println("ndnddndnmddnmdnm");
        return new ResponseEntity<>(service.create(enrollment), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<enrollmentsDTO>> getAllEnrollments() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StudentEnrollmentDetails> getOneEnrollment(@PathVariable("id") int id) throws Exception {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    // @PutMapping("/update/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public void updateOneEnrollment(@PathVariable("id") int id, @RequestBody StudentEnrollmentModel enrollment){
    //     service.updateStudentEnrollmentById(id, enrollment);
    // }

    // @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<StudentEnrollment> patchOneEnrollment(@PathVariable("id") int id, @RequestBody JsonPatch patch){
    //     return new ResponseEntity<>(service.patchOne(id, patch), HttpStatus.OK);
    // }

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
