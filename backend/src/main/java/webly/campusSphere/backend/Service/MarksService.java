package webly.campusSphere.backend.Service;

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
import webly.campusSphere.backend.DTOs.MarksServiceDTOs.MarksDetails;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.Marks;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.BaseModel.MarksModel;
import webly.campusSphere.backend.Repository.CourseRepository;
import webly.campusSphere.backend.Repository.MarksRepository;
import webly.campusSphere.backend.Repository.UserRepository;

@Service
public class MarksService {
    private final MarksRepository marksRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ObjectMapper objectMapper;

    public MarksService(MarksRepository marksRepository, UserRepository userRepository, CourseRepository courseRepository, ObjectMapper objectMapper) {
        this.marksRepository = marksRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public MarksDetails create(MarksModel marksModel) {
        User student = userRepository.findById(marksModel.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + marksModel.getStudentId()));

        Course course = courseRepository.findById(marksModel.getCourseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + marksModel.getCourseId()));

        Marks marksToSave = Marks.builder()
                .candidate(student)
                .course(course)
                .marks(marksModel.getMarks())
                .build();

        return MarksDetails.generateDTO(marksRepository.save(marksToSave));
    }

    public MarksDetails findById(long id) {
        return MarksDetails.generateDTO(marksRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Marks record not found with id: " + id)));
    }

    public List<MarksDetails> findAll() {
        return marksRepository.findAll().stream()
                .sorted(Comparator.comparing(Marks::getId))
                .map(marks -> MarksDetails.generateDTO(marks))
                .collect(Collectors.toList());
    }

    // @Transactional
    // public void updateMarksById(long id, MarksModel marksModel) {
    //     Marks existingMarks = marksRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Marks record not found with id: " + id));

    //     User student = userRepository.findById(marksModel.getStudentId())
    //             .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + marksModel.getStudentId()));

    //     Course course = courseRepository.findById(marksModel.getCourseId())
    //             .orElseThrow(() -> new EntityNotFoundException("Course not found with id: " + marksModel.getCourseId()));

    //     existingMarks.setStudent(student);
    //     existingMarks.setCourse(course);
    //     existingMarks.setSemester(marksModel.getSemester());
    //     existingMarks.setMarks(marksModel.getMarks());

    //     marksRepository.save(existingMarks);
    // }

    // @Transactional
    // public Marks patchOne(long id, JsonPatch patch) {
    //     Marks marks = marksRepository.findById(id)
    //             .orElseThrow(() -> new EntityNotFoundException("Marks record not found with id: " + id));

    //     Marks marksPatched = applyPatchToMarks(patch, marks);
    //     return marksRepository.save(marksPatched);
    // }

    @Transactional
    public void deleteById(long id) {
        if (!marksRepository.existsById(id)) {
            throw new EntityNotFoundException("Marks record not found with id: " + id);
        }
        marksRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        marksRepository.deleteAll();
    }

    // private Marks applyPatchToMarks(JsonPatch patch, Marks marks) {
    //     try {
    //         JsonNode patched = patch.apply(objectMapper.convertValue(marks, JsonNode.class));
    //         return objectMapper.treeToValue(patched, Marks.class);
    //     } catch (JsonPatchException | JsonProcessingException e) {
    //         throw new RuntimeException("Failed to apply JSON patch: " + e.getMessage(), e);
    //     }
    // }
}