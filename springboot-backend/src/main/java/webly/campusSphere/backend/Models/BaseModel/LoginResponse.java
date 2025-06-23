package webly.campusSphere.backend.Models.BaseModel;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LoginResponse {

    private String token;
    private String role;
    private long expiresIn;

}
