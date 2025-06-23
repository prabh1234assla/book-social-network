package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
class studentDTO {
    private String studentName;
    private String studentEmail;
    private Integer semester;
    private boolean isSemesterRegistrationDone; 
}
