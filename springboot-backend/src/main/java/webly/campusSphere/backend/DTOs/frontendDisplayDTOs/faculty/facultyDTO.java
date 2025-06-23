package webly.campusSphere.backend.DTOs.frontendDisplayDTOs.faculty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.User;

@Getter
@Setter
@Builder
public class facultyDTO {
    private String facultyName;
    private String facultyEmail;

    public static facultyDTO generateDTO(User user){
        return facultyDTO.builder()
                            .facultyName(user.getUsername())
                            .facultyEmail(user.getEmail())
                            .build();
    }
}
