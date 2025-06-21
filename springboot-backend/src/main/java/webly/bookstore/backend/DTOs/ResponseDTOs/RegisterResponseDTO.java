package webly.bookstore.backend.DTOs.ResponseDTOs;
import lombok.Setter;

@Setter
public class RegisterResponseDTO {
    private long id;
    private String role;
    private String username;
    private String email;
}
