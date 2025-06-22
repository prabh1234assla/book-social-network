package webly.bookstore.backend.DTOs.CourseServiceDTOs;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import webly.bookstore.backend.Models.Course;
import webly.bookstore.backend.Models.User;

@Setter
public class CourseDetails {
    private @Getter @Setter long id;
    private @Getter @Setter long facultyId;
    private @Getter @Setter String courseName;
    private @Getter @Setter BigDecimal marks;
    private @Getter @Setter Set<Long> studentsId;

    public static CourseDetails generateDTO(Course course){
        CourseDetails dto = new CourseDetails();

        dto.setCourseName(course.getCourseName());
        dto.setId(course.getId());
        dto.setFacultyId(course.getFaculty().getId());
        dto.setMarks(course.getMarks());

        Set<User> students = course.getStudents();
        Set<Long> studentsId = new HashSet<>();

        for (User student : students){
            studentsId.add(student.getId());
        }

        dto.setStudentsId(studentsId);
        
        return dto;
    }
}
