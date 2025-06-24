package webly.campusSphere.backend.DTOs.CourseServiceDTOs;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.persistence.EntityNotFoundException;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Course;
import webly.campusSphere.backend.Models.User;

@Setter
public class CourseDetails {
    private @Getter @Setter long id;
    private @Getter @Setter long facultyId;
    private @Getter @Setter String courseName;
    private @Getter @Setter Set<Long> studentsId;
    private @Getter @Setter Integer credits;

    public static CourseDetails generateDTO(Course course){
        CourseDetails dto = new CourseDetails();

        dto.setCourseName(course.getCourseName());
        dto.setId(course.getId());
        dto.setFacultyId(course.getFaculty().getId());
        dto.setCredits(course.getCredits());

        Set<User> students = course.getStudents();
        Set<Long> studentsId = new HashSet<>();

        for (User student : students){
            studentsId.add(student.getId());
        }

        dto.setStudentsId(studentsId);
        
        return dto;
    }
}
