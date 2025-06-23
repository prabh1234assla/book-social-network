package webly.campusSphere.backend.Models.BaseModel;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import webly.campusSphere.backend.Models.Utils.UserRole;

@Getter
@Setter
@Component
public class UserModel {

    private long id;
    private UserRole role;
    private String username;
    private String email;
    private String password;
    private Integer semester;
    private boolean isStudentEnrolled;
    
}