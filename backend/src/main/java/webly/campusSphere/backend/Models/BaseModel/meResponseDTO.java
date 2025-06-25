package webly.campusSphere.backend.Models.BaseModel;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.Utils.UserRole;

@Getter
@Setter
@Builder
public class meResponseDTO {

    private String role;
    private String username;
    private String email;
    private Integer semester;
    private boolean isStudentEnrolled;

    public static meResponseDTO generateDTO(User user){
        return meResponseDTO.builder()
                                .role(user.getRole().toString())
                                .username(user.getUsername())
                                .email(user.getEmail())
                                .semester(user.getSemester())
                                .isStudentEnrolled(user.isStudentEnrolled())
                                .build();
    }
    
}