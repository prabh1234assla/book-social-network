package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.User;

@Setter
@Getter
@Builder
public class studentDTO {
    private String studentName;
    private String studentEmail;
    private Integer semester;
    private boolean isSemesterRegistrationDone; 

    public static studentDTO generateDTO(User user){
        return studentDTO.builder()
                            .studentName(user.getUsername())
                            .studentEmail(user.getEmail())
                            .semester(user.getSemester())
                            .isSemesterRegistrationDone(user.isStudentEnrolled())
                            .build();
    }
}
