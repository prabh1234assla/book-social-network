package webly.bookstore.backend.DTOs.ResponseDTOs;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegisterResponseDTO {
    private long id;
    private String role;
    private String username;
    private String email;
}
