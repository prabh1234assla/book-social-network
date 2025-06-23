package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
public class enrollmentsDTO {
    private String studentName;
    private String studentEmail;
    private String courseName;
    private Integer credits;
    private Integer semester;
    private boolean isRegisteredInCourse; 
}
