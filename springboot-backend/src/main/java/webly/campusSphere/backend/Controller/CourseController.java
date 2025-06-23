package webly.campusSphere.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.fge.jsonpatch.JsonPatch;

import webly.campusSphere.backend.DTOs.CourseServiceDTOs.CourseDetails;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin.coursesDTO;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.CourseModel;
import webly.campusSphere.backend.Models.Utils.UserRole;
import webly.campusSphere.backend.Service.CourseService;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService service;

    public CourseController(CourseService service) {
        this.service = service;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PostMapping("/")
    public ResponseEntity<CourseDetails> createCourse(@RequestBody CourseModel course) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to create a course!");
        }

        System.out.println("excuse me");

        return new ResponseEntity<>(service.create(course), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<coursesDTO>> getAllCourses() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseDetails> getOneCourse(@PathVariable("id") int id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    // @GetMapping("/faculty/{id}")
    // public ResponseEntity<List<CourseDetails>> getCoursesTaughtByFaculty(@PathVariable("id") int id) {
    //     return new ResponseEntity<>(service.findAllByFacultyId(id), HttpStatus.OK);
    // }

    // @PutMapping("/update/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public void updateOneCourse(@PathVariable("id") int id, @RequestBody CourseModel course) {
    //     service.updateCourseById(id, course);
    // }

    // @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Course> patchOneCourse(@PathVariable("id") int id, @RequestBody JsonPatch patch) {
    //     return new ResponseEntity<>(service.patchOne(id, patch), HttpStatus.OK);
    // }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneCourse(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete a course!");
        }
        service.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllCourses() throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete a course!");
        }
        service.deleteAll();
    }
}
