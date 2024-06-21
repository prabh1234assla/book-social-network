package webly.bookstore.backend.Controller;

import java.util.List;

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

import com.github.fge.jsonpatch.JsonPatch;

import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.UserModel;
import webly.bookstore.backend.Service.UserService;


@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service){
        this.service = service;
    }

    // create user
    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserModel user){
        return new ResponseEntity<>(service.create(user), HttpStatus.CREATED);
    }

    // get all users
    @GetMapping()
    public ResponseEntity<List <User>> getAllUsers(){
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    // get user with specific id
    @GetMapping("/{id}")
    public ResponseEntity<User> getOneUser(@PathVariable("id") int id){
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    // update a user with specific id
    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneUser(@PathVariable("id") int id, @RequestBody UserModel book){
        service.updateOne(id, book);
    }

    // update specific info of user with a specific id
    @PatchMapping(value = "/update/{id}",  consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> patchOneUser(@PathVariable("id") int id, @RequestBody JsonPatch patch){
        return new ResponseEntity<>(service.patchOne(id, patch), HttpStatus.OK);
    }

    // delete user with specific id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneUser(@PathVariable("id") int id){
        service.deleteById(id);
    }

    // delete all users
    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllUsers(){
        service.deleteAll();
    }
}
