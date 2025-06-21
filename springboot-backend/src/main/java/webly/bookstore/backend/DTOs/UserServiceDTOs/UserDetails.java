package webly.bookstore.backend.DTOs.UserServiceDTOs;

import lombok.Setter;
import webly.bookstore.backend.Models.User;

public class UserDetails {
    private @Setter long id;
    private @Setter String role;
    private @Setter String username;
    private @Setter String email;

    public static UserDetails generaDto(User user){
        UserDetails dto = new UserDetails();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());

        return dto;
    }

}
