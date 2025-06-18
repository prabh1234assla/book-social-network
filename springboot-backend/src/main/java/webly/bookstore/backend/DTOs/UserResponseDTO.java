package webly.bookstore.backend.DTOs;

import lombok.Setter;
import lombok.Getter;

@Setter
@Getter
public class UserResponseDTO {
    private Long id;
    private String username;
    private String email;
    private String role;
}
