package webly.bookstore.backend.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import webly.bookstore.backend.DTOs.LogindDTO;
import webly.bookstore.backend.DTOs.RegisterDTO;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.Utils.UserRole;
import webly.bookstore.backend.Repository.UserRepository;

@Service
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
        UserRepository userRepository,
        PasswordEncoder passwordEncoder,
        AuthenticationManager authenticationManager
    ){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    public User Signup(RegisterDTO registerDTO){

        String username = registerDTO.getUsername();
        UserRole role = UserRole.ADMIN;

        if(username.endsWith("_std"))
            role = UserRole.STUDENT;
        else if(username.startsWith("_fac"))
            role = UserRole.FACULTY;

        userRepository.findByUsername(username).ifPresent(user -> { throw new webly.bookstore.backend.exceptions.AuthenticationException("user already exists"); });

        User registeredUser = User.builder().
                                    role(role).
                                    email(registerDTO.getEmail()).
                                    username(username).
                                    password(passwordEncoder.encode(registerDTO.getPassword())).
                                    build();                                    

        return userRepository.save(registeredUser);
    }

    public User Authenticate(LogindDTO logindDTO){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindDTO.getUsername(), logindDTO.getPassword())
            );

            return userRepository.findByUsername(logindDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        } catch (AuthenticationException e){
            throw new webly.bookstore.backend.exceptions.AuthenticationException(e.getMessage());
        }
    }
}
