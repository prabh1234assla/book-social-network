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
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin.coursesDTO;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin.facultyDTO;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin.studentDTO;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student.enrollmentsDTO;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student.feesDTO;
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student.marksDTO;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.Fee;
import webly.campusSphere.backend.Models.Marks;
import webly.campusSphere.backend.Models.StudentEnrollment;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.Utils.UserRole;
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

    public List<studentDTO> findAllStudents() throws EntityNotFoundException{
        List<studentDTO> userDetails = repository.findAll().stream().sorted(Comparator.comparing(User::getId)).filter(user -> (user.getRole() == UserRole.STUDENT)).map(user -> studentDTO.generateDTO(user)).collect(Collectors.toList());

        if (userDetails.isEmpty() == true)
                throw new EntityNotFoundException("User Details Are Empty");

        return userDetails;
    }

    public List<facultyDTO> findAllFaculty() throws EntityNotFoundException{
        List<facultyDTO> userDetails = repository.findAll().stream().sorted(Comparator.comparing(User::getId)).filter(user -> (user.getRole() == UserRole.FACULTY)).map(user -> facultyDTO.generateDTO(user)).collect(Collectors.toList());

        if (userDetails.isEmpty() == true)
                throw new EntityNotFoundException("User Details Are Empty");

        return userDetails;
    }

    public List<coursesDTO> findAllCoursesAssociated(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Course> coursesTaught = user.getCoursesTaught();
        List<coursesDTO> courseDetails = coursesTaught.stream().map(course -> coursesDTO.generaDto(course)).collect(Collectors.toList());

        return courseDetails;
    }

    public List<feesDTO> findAllFees(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Fee> fees = user.getFees();
        List<feesDTO> feeDetails = fees.stream().map(fee -> feesDTO.generaDto(fee)).collect(Collectors.toList());

        return feeDetails;
    }

    public List<enrollmentsDTO> findAllEnrollments(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<StudentEnrollment> enrollments = user.getStudentEnrollments();
        List<enrollmentsDTO> enrollmentDetails = enrollments.stream().map(enrollment -> enrollmentsDTO.generaDto(enrollment)).collect(Collectors.toList());

        return enrollmentDetails;
    }

    public List<marksDTO> findAllMarks(long id) throws EntityNotFoundException{
        
        User user = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        Set<Marks> marks = user.getMarks();
        List<marksDTO> marksDetails = marks.stream().map(mark -> marksDTO.generateDto(mark)).collect(Collectors.toList());

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
