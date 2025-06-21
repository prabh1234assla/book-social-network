package webly.bookstore.backend.DTOs.CourseServiceDTOs;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import webly.bookstore.backend.Models.Course;

@Setter
public class CourseDetails {
    private @Getter @Setter long id;
    private @Getter @Setter long facultyId;
    private @Getter @Setter String courseName;
    private @Getter @Setter BigDecimal marks;

    public static CourseDetails generateDTO(Course course){
        CourseDetails dto = new CourseDetails();

        dto.setCourseName(course.getCourseName());
        dto.setId(course.getId());
        dto.setFacultyId(course.getFaculty().getId());
        dto.setMarks(course.getMarks());

        return dto;
    }
}
