package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Course;

@Getter
@Setter
@Builder
public class coursesDTO {
    private String courseName;
    private Integer credits;

    public static coursesDTO generaDto(Course course){
        return coursesDTO.builder()
                            .courseName(course.getCourseName())
                            .credits(course.getCredits())
                            .build();
    }
}
