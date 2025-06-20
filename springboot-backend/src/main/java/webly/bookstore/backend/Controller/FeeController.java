package webly.bookstore.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import webly.bookstore.backend.Models.Fee;
import webly.bookstore.backend.Models.BaseModel.FeeModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.Utils.UserRole;
import webly.bookstore.backend.Service.FeeService;


import java.util.List;

@RestController
@RequestMapping("/fee")
public class FeeController {

    private final FeeService service;

    public FeeController(FeeService service) {
        this.service = service;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    @PostMapping("/")
    public ResponseEntity<Fee> createFee(@RequestBody FeeModel fee) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to create fee record!");
        }
        return new ResponseEntity<>(service.create(fee), HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<List<FeeModel>> getAllFees() throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to see fee records!");
        }
        System.out.println("kjdjdkjdn");
        return new ResponseEntity<>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeeModel> getOneFee(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();

        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to see a fee records!");
        }

        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateOneFee(@PathVariable("id") int id, @RequestBody FeeModel fee){
        service.updateFeeById(id, fee);
    }

    @PatchMapping(value = "/{id}", consumes = "application/json-patch+json")
    public ResponseEntity<FeeModel> patchOneFee(@PathVariable("id") int id, @RequestBody JsonPatch patch) throws JsonPatchException{
        System.out.println("csnkknmskmds");
        System.out.println(patch);
        FeeModel updatedFee = service.patchOne(id, patch);
        return ResponseEntity.ok(updatedFee);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteOneFee(@PathVariable("id") int id) throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete a fee record!");
        }
        service.deleteById(id);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllFees() throws Exception {
        User currentUser = getAuthenticatedUser();
        if (currentUser.getRole() != UserRole.ADMIN) {
            throw new Exception("Gain admin privileges to delete all fees!");
        }
        service.deleteAll();
    }
}
