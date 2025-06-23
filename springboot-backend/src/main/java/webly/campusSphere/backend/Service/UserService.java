package webly.campusSphere.backend.Service;

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
import webly.campusSphere.backend.DTOs.CourseServiceDTOs.CourseDetails;
import webly.campusSphere.backend.DTOs.FeeServiceDTOs.FeeDetails;
import webly.campusSphere.backend.DTOs.MarksServiceDTOs.MarksDetails;
import webly.campusSphere.backend.DTOs.StudentEnrollmentServiceDTOs.StudentEnrollmentDetails;
import webly.campusSphere.backend.DTOs.UserServiceDTOs.UserDetails;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.Fee;
import webly.campusSphere.backend.Models.Marks;
import webly.campusSphere.backend.Models.StudentEnrollment;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Repository.UserRepository;

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

    public List<CourseDetails> findAllCoursesAssociated(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Course> coursesTaught = user.getCoursesTaught();
        List<CourseDetails> courseDetails = coursesTaught.stream().map(course -> CourseDetails.generateDTO(course)).collect(Collectors.toList());

        return courseDetails;
    }

    public List<FeeDetails> findAllFees(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Fee> fees = user.getFees();
        List<FeeDetails> feeDetails = fees.stream().map(fee -> FeeDetails.generateDTO(fee)).collect(Collectors.toList());

        return feeDetails;
    }

    public List<StudentEnrollmentDetails> findAllEnrollments(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<StudentEnrollment> enrollments = user.getStudentEnrollments();
        List<StudentEnrollmentDetails> enrollmentDetails = enrollments.stream().map(enrollment -> StudentEnrollmentDetails.generateDTO(enrollment)).collect(Collectors.toList());

        return enrollmentDetails;
    }

    public List<MarksDetails> findAllMarks(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Marks> marks = user.getMarks();
        List<MarksDetails> marksDetails = marks.stream().map(mark -> MarksDetails.generateDTO(mark)).collect(Collectors.toList());

        return marksDetails;
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
