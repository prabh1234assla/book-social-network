package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.StudentEnrollment;

@Builder
@Setter
@Getter
public class enrollmentsDTO {
    private String courseName;
    private Integer credits;
    private boolean isRegisteredInCourse; 

    public static enrollmentsDTO generaDto(StudentEnrollment enrollment){
        return enrollmentsDTO.builder()
                                .courseName(enrollment.getCourse().getCourseName())
                                .credits(enrollment.getCourse().getCredits())
                                .isRegisteredInCourse(enrollment.isRegistered())
                                .build();
    }
}
