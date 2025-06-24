package webly.campusSphere.backend.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
import webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin.coursesDTO;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.CourseModel;
import webly.campusSphere.backend.Repository.CourseRepository;
import webly.campusSphere.backend.Repository.UserRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final MarksService marksService;
    private final StudentEnrollmentService StudentEnrollmentService;

    private final ObjectMapper objectMapper;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, ObjectMapper objectMapper, MarksService marksService, StudentEnrollmentService StudentEnrollmentService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
        this.marksService = marksService;
        this.StudentEnrollmentService = StudentEnrollmentService;
    }

    @Transactional
    public CourseDetails create(CourseModel courseModel) {
        User faculty = userRepository.findById(courseModel.getFacultyId())
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + courseModel.getFacultyId()));

        Set<Long> studentsId = courseModel.getStudentsId();
        System.out.println(studentsId);
        Set<User> students = new HashSet<>();

        for (Long id: studentsId){
            User student = userRepository.findById(id)
                    .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + id)); 

            System.out.println(student);

            students.add(student);
        }
        

        System.out.println("dsnjdnd");

        Course courseToSave = Course.builder()
                .faculty(faculty)
                .courseName(courseModel.getCourseName())
                .credits(courseModel.getCredits())
                .students(students)
                .build();

        return CourseDetails.generateDTO(courseRepository.save(courseToSave));
    }

    public CourseDetails findById(long id) {
        return CourseDetails.generateDTO(courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id)));
    }

    public List<coursesDTO> findAll() {
        return courseRepository.findAll().stream()
                .sorted(Comparator.comparing(Course::getId))
                .map(course -> coursesDTO.generaDto(course))
                .collect(Collectors.toList());
    }

    public List<webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty.coursesDTO> findAllByFacultyId(long id) {
        return courseRepository.listCoursesTaughtByGivenFaculty(id).stream()
                .sorted(Comparator.comparing(Course::getId))
                .map(course -> webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty.coursesDTO.generaDto(course))
                .collect(Collectors.toList());
    }

    // @Transactional
    // public void updateCourseById(long id, CourseModel courseModel) {
    //     Course existingCourse = courseRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

    //     User faculty = userRepository.findById(courseModel.getFacultyId())
    //             .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + courseModel.getFacultyId()));

    //     existingCourse.setFaculty(faculty);
    //     existingCourse.setCourseName(courseModel.getCourseName());

    //     courseRepository.save(existingCourse);
    // }

    // @Transactional
    // public Course patchOne(long id, JsonPatch patch) {
    //     Course course = courseRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

    //     Course coursePatched = applyPatchToCourse(patch, course);
    //     return courseRepository.save(coursePatched);
    // }

    @Transactional
    public void deleteById(long id) {
        if (!courseRepository.existsById(id)) {
            throw new EntityNotFoundException("Course not found with id: " + id);
        }
        courseRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        courseRepository.deleteAll();
    }

    // private Course applyPatchToCourse(JsonPatch patch, Course course) {
    //     try {
    //         JsonNode patched = patch.apply(objectMapper.convertValue(course, JsonNode.class));
    //         return objectMapper.treeToValue(patched, Course.class);
    //     } catch (JsonPatchException | JsonProcessingException e) {
    //         throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
    //     }
    // }
}