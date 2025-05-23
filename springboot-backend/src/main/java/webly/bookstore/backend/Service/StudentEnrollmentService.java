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
import webly.bookstore.backend.Models.StudentEnrollment;
import webly.bookstore.backend.Models.BaseModel.StudentEnrollmentModel;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Repository.CourseRepository;
import webly.bookstore.backend.Repository.StudentEnrollmentRepository;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class StudentEnrollmentService {
    private final StudentEnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;

    public StudentEnrollmentService(StudentEnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, ObjectMapper objectMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public StudentEnrollment create(StudentEnrollmentModel enrollmentModel) {
        User student = userRepository.findById(enrollmentModel.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + enrollmentModel.getStudentId()));

        Course course = courseRepository.findById(enrollmentModel.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + enrollmentModel.getCourseId()));

        StudentEnrollment enrollmentToSave = StudentEnrollment.builder()
                .student(student)
                .course(course)
                .semester(enrollmentModel.getSemester())
                .isRegistered(enrollmentModel.getIsRegistered() != null ? enrollmentModel.getIsRegistered() : false)
                .build();

        return enrollmentRepository.save(enrollmentToSave);
    }

    public StudentEnrollment findById(long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + id));
    }

    public List<StudentEnrollment> findAll() {
        return enrollmentRepository.findAll().stream()
                .sorted(Comparator.comparing(StudentEnrollment::getId))
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateStudentEnrollmentById(long id, StudentEnrollmentModel enrollmentModel) {
        StudentEnrollment existingEnrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + id));

        User student = userRepository.findById(enrollmentModel.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + enrollmentModel.getStudentId()));

        Course course = courseRepository.findById(enrollmentModel.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + enrollmentModel.getCourseId()));

        existingEnrollment.setStudent(student);
        existingEnrollment.setCourse(course);
        existingEnrollment.setSemester(enrollmentModel.getSemester());
        existingEnrollment.setRegistered(enrollmentModel.getIsRegistered() != null ? enrollmentModel.getIsRegistered() : existingEnrollment.isRegistered());

        enrollmentRepository.save(existingEnrollment);
    }

    @Transactional
    public StudentEnrollment patchOne(long id, JsonPatch patch) {
        StudentEnrollment enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Enrollment not found with id: " + id));

        StudentEnrollment enrollmentPatched = applyPatchToEnrollment(patch, enrollment);
        return enrollmentRepository.save(enrollmentPatched);
    }

    @Transactional
    public void deleteById(long id) {
        if (!enrollmentRepository.existsById(id)) {
            throw new EntityNotFoundException("Enrollment not found with id: " + id);
        }
        enrollmentRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        enrollmentRepository.deleteAll();
    }

    private StudentEnrollment applyPatchToEnrollment(JsonPatch patch, StudentEnrollment enrollment) {
        try {
            JsonNode patched = patch.apply(objectMapper.convertValue(enrollment, JsonNode.class));
            return objectMapper.treeToValue(patched, StudentEnrollment.class);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
        }
    }
}