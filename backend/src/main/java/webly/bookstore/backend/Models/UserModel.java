package webly.bookstore.backend.Models;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class UserModel {

    private long id;
    private Role role;
    private String username;
    private String email;
    private String password;

}