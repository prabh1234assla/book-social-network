package webly.bookstore.backend.DTOs.UserServiceDTOs;

import lombok.Getter;
import lombok.Setter;
import webly.bookstore.backend.Models.User;


public class UserDetails {
    private @Getter @Setter long id;
    private @Getter @Setter String role;
    private @Getter @Setter String username;
    private @Getter @Setter String email;

    public static UserDetails generaDto(User user){
        UserDetails dto = new UserDetails();

        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(user.getRole().toString());

        return dto;
    }

}
