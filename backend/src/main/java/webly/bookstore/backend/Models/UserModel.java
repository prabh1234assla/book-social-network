package webly.bookstore.backend.Models;

import java.util.Set;

import org.springframework.stereotype.Component;

import lombok.Getter;

@Getter
@Component
public class UserModel {

    private long id;
    private UserRole role;
    private String username;
    private String email;
    private String password;
    private Set<Book> books;

}