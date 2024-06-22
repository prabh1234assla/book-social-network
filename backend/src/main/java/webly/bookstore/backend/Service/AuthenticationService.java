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
import webly.bookstore.backend.Models.UserRole;
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

        User registeredUser = User.builder().
                                    role(UserRole.USER).
                                    email(registerDTO.getEmail()).
                                    username(registerDTO.getUsername()).
                                    password(passwordEncoder.encode(registerDTO.getPassword())).
                                    build();                                    

        return userRepository.save(registeredUser);

    }

    public User Authenticate(LogindDTO logindDTO){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindDTO.getEmail(), logindDTO.getPassword())
            );
        } catch (AuthenticationException e){
            System.out.println(e.getMessage());
        }

        return userRepository.findByEmail(logindDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
