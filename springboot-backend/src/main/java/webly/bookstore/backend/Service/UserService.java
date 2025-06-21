package webly.bookstore.backend.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.persistence.EntityNotFoundException;
import webly.bookstore.backend.DTOs.UserServiceDTOs.UserDetails;
import webly.bookstore.backend.Models.Course;
import webly.bookstore.backend.Models.Fee;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class UserService {
    private final UserRepository repository;
    private final CourseService courseService;
    private final FeeService feeService;
    private final ObjectMapper objectMapper;

    public UserService(UserRepository repository, CourseService courseService, FeeService feeService, ObjectMapper objectMapper){
        this.repository = repository;
        this.courseService = courseService;
        this.feeService = feeService;
        this.objectMapper = objectMapper;
    }

    public UserDetails findById(long id) throws EntityNotFoundException {
        return UserDetails.generaDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public List<UserDetails> findAll() throws EntityNotFoundException{
        List<UserDetails> userDetails = repository.findAll().stream().sorted(Comparator.comparing(User::getId)).map(user -> UserDetails.generaDto(user)).collect(Collectors.toList());

        if (userDetails.isEmpty() == true)
                throw new EntityNotFoundException("User Details Are Empty");

        return userDetails;
    }

    // public User patchOne(long id, JsonPatch patch){
    //     User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);

    //     User userPatched = applyPatchToUser(patch, user);

    //     return repository.save(userPatched);
    // }

    @Transactional
    public void deleteById(long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }

        Set<Course> coursesTaught = repository.findById(id).get().getCoursesTaught();
        for (Course course: coursesTaught){
            courseService.deleteById(course.getId());
        }

        Set<Fee> fees = repository.findById(id).get().getFees();
        for (Fee fee: fees){
            feeService.deleteById(fee.getId());
        }

        repository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        courseService.deleteAll();
        feeService.deleteAll();
        repository.deleteAll();
    }

    // public List<UserResponseDTO> getAllUsersExceptCurrent(User currentUser) {
    //     return repository.findAll().stream()
    //         .filter(user -> user.getId() != currentUser.getId()) // exclude current user
    //         .map(user -> {
    //             UserResponseDTO dto = new UserResponseDTO();
    //             dto.setId(user.getId());
    //             dto.setUsername(user.getUsername());
    //             dto.setEmail(user.getEmail());
    //             dto.setRole(user.getRole().toString());
    //             return dto;
    //         })
    //         .sorted(Comparator.comparing(UserResponseDTO::getId)) // optional
    //         .toList();
    // }

    // private User applyPatchToUser(JsonPatch patch, User user){
    //     try{
    //         JsonNode patched = patch.apply(objectMapper.convertValue(user, JsonNode.class));
    //         return objectMapper.treeToValue(patched, User.class);
    //     } catch (JsonPatchException | JsonProcessingException e) {
    //         throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
    //     }
    // }
}
