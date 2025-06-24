package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.StudentEnrollment;

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

    public static enrollmentsDTO generaDto(StudentEnrollment enrollment){
        return enrollmentsDTO.builder()
                                .studentName(enrollment.getStudentEnrolled().getUsername())
                                .studentEmail(enrollment.getStudentEnrolled().getEmail())
                                .courseName(enrollment.getCourse().getCourseName())
                                .credits(enrollment.getCourse().getCredits())
                                .semester(enrollment.getStudentEnrolled().getSemester())
                                .isRegisteredInCourse(enrollment.isRegistered())
                                .build();
    }
}
