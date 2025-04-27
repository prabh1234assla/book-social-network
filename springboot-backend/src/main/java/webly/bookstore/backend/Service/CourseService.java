package webly.bookstore.backend.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import jakarta.persistence.EntityNotFoundException;
import webly.bookstore.backend.Models.Course;
import webly.bookstore.backend.Models.BaseModel.CourseModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Repository.CourseRepository;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public CourseService(CourseRepository courseRepository, UserRepository userRepository, ObjectMapper objectMapper) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public Course create(CourseModel courseModel) {
        User faculty = userRepository.findById(courseModel.getFacultyId())
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + courseModel.getFacultyId()));

        Course courseToSave = Course.builder()
                .faculty(faculty)
                .courseName(courseModel.getCourseName())
                .enrollments(null) // These will be managed separately
                .marks(null)       // These will be managed separately
                .build();

        return courseRepository.save(courseToSave);
    }

    public Course findById(long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));
    }

    public List<Course> findAll() {
        return courseRepository.findAll().stream()
                .sorted(Comparator.comparing(Course::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateCourseById(long id, CourseModel courseModel) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        User faculty = userRepository.findById(courseModel.getFacultyId())
                .orElseThrow(() -> new EntityNotFoundException("Faculty not found with id: " + courseModel.getFacultyId()));

        existingCourse.setFaculty(faculty);
        existingCourse.setCourseName(courseModel.getCourseName());

        courseRepository.save(existingCourse);
    }

    @Transactional
    public Course patchOne(long id, JsonPatch patch) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + id));

        Course coursePatched = applyPatchToCourse(patch, course);
        return courseRepository.save(coursePatched);
    }

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

    private Course applyPatchToCourse(JsonPatch patch, Course course) {
        try {
            JsonNode patched = patch.apply(objectMapper.convertValue(course, JsonNode.class));
            return objectMapper.treeToValue(patched, Course.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
        }
    }
}