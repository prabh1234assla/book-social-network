package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Course;

@Getter
@Setter
@Builder
public class coursesDTO {
    private String facultyName;
    private String facultyEmail;
    private String courseName;
    private Integer credits;

    public static coursesDTO generaDto(Course course){
        return coursesDTO.builder()
                            .facultyName(course.getFaculty().getUsername())
                            .facultyEmail(course.getFaculty().getEmail())
                            .courseName(course.getCourseName())
                            .credits(course.getCredits())
                            .build();
    }
}
