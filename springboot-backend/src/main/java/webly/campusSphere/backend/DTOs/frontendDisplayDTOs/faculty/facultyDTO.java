package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class facultyDTO {
    private String facultyName;
    private String facultyEmail;
}
