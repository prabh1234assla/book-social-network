package webly.bookstore.backend.DTOs.CourseServiceDTOs;

import java.math.BigDecimal;
import java.util.stream.Collectors;

import lombok.Setter;
import webly.bookstore.backend.Models.Course;

@Setter
public class CourseDetails {
    private @Setter long id;
    private @Setter long facultyId;
    private @Setter String courseName;
    private @Setter BigDecimal marks;

    public static CourseDetails generateDTO(Course course){
        CourseDetails dto = new CourseDetails();

        dto.setCourseName(course.getCourseName());
        dto.setId(course.getId());
        dto.setFacultyId(course.getFaculty().getId());
        dto.setMarks(course.getMarks());

        return dto;
    }
}
