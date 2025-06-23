package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.student;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class enrollmentsDTO {
    private String courseName;
    private Integer credits;
    private boolean isRegisteredInCourse; 
}
