package webly.campusSphere.backend.Service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import webly.campusSphere.backend.DTOs.LogindDTO;
import webly.campusSphere.backend.DTOs.RegisterDTO;
import webly.campusSphere.backend.DTOs.ResponseDTOs.RegisterResponseDTO;
import webly.campusSphere.backend.Models.User;
import webly.campusSphere.backend.Models.Utils.UserRole;
import webly.campusSphere.backend.Repository.UserRepository;

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

    public RegisterResponseDTO Signup(RegisterDTO registerDTO){

        String username = registerDTO.getUsername();
        UserRole role = UserRole.ADMIN;

        if(username.endsWith("_std"))
            role = UserRole.STUDENT;
        else if(username.endsWith("_fac"))
            role = UserRole.FACULTY;

        userRepository.findByUsername(username).ifPresent(user -> { throw new webly.campusSphere.backend.exceptions.AuthenticationException("user already exists"); });

        User registeredUser = User.builder().
                                    role(role).
                                    email(registerDTO.getEmail()).
                                    username(username).
                                    password(passwordEncoder.encode(registerDTO.getPassword())).
                                    isStudentEnrolled(false).
                                    semester(0).
                                    build();                                    

        userRepository.save(registeredUser);

        RegisterResponseDTO dto = new RegisterResponseDTO();

        dto.setId(registeredUser.getId());
        dto.setEmail(registeredUser.getEmail());
        dto.setUsername(registeredUser.getUsername());
        dto.setRole(registeredUser.getRole().toString());

        return dto;
    }

    public User Authenticate(LogindDTO logindDTO){
        try{
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(logindDTO.getUsername(), logindDTO.getPassword())
            );

            return userRepository.findByUsername(logindDTO.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found"));

        } catch (AuthenticationException e){
            throw new webly.campusSphere.backend.exceptions.AuthenticationException(e.getMessage());
        }
    }
}
