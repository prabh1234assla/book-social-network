package webly.campusSphere.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.fge.jsonpatch.JsonPatch;

import webly.campusSphere.backend.DTOs.MarksServiceDTOs.MarksDetails;
import webly.campusSphere.backend.Models.Marks;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.MarksModel;
import webly.campusSphere.backend.Models.Utils.UserRole;
import webly.campusSphere.backend.Service.MarksService;

import java.util.List;

@RestController
@RequestMapping("/marks")
public class MarksController {

    private final MarksService service;

    public MarksController(MarksService service) {
        this.service = service;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PostMapping("/")
    public ResponseEntity<MarksDetails> createMarks(@RequestBody MarksModel marks) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to create marks!");
        }
        return new ResponseEntity<>(service.create(marks), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<MarksDetails>> getAllMarks() {
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarksDetails> getOneMarks(@PathVariable("id") int id) throws Exception {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    // @PutMapping("/update/{id}")
    // @ResponseStatus(HttpStatus.OK)
    // public void updateOneMarks(@PathVariable("id") int id, @RequestBody MarksModel marks) throws Exception {
    //     User currentUser = getAuthenticatedUser();
    //     if (currentUser.getRole() == UserRole.STUDENT) {
    //         throw new Exception("Students cannot update marks!");
    //     }
    //     service.updateMarksById(id, marks);
    // }

    // @PatchMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    // public ResponseEntity<Marks> patchOneMarks(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws Exception {
    //     User currentUser = getAuthenticatedUser();
    //     if (currentUser.getRole() == UserRole.STUDENT) {
    //         throw new Exception("Students cannot patch marks!");
    //     }
    //     return new ResponseEntity<>(service.patchOne(id, patch), HttpStatus.OK);
    // }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneMarks(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete mark!");
        }
        service.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllMarks() throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete marks!");
        }
        service.deleteAll();
    }
}
