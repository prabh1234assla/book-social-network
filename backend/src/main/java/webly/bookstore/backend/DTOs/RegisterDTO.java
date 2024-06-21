package webly.bookstore.backend.DTOs;

import lombok.Getter;

@Getter
public class RegisterDTO {
    private String username;
    private String email;
    private String password;
}
