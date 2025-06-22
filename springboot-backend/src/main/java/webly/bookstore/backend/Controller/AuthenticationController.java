package webly.bookstore.backend.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webly.bookstore.backend.DTOs.LogindDTO;
import webly.bookstore.backend.DTOs.RegisterDTO;
import webly.bookstore.backend.DTOs.ResponseDTOs.RegisterResponseDTO;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Models.BaseModel.LoginResponse;
import webly.bookstore.backend.Service.AuthenticationService;
import webly.bookstore.backend.Service.JwtService;
import webly.bookstore.backend.exceptions.AuthenticationException;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(
            JwtService jwtService,
            AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> Register(@RequestBody RegisterDTO registerDTO) {
        try {
            RegisterResponseDTO response = authenticationService.Signup(registerDTO);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + ex.getMessage());
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> Authenticate(@RequestBody LogindDTO logindDTO) {
        try {
            User authenticatedUser = authenticationService.Authenticate(logindDTO);

            String jwtToken = jwtService.generateToken(authenticatedUser);

            LoginResponse loginResponse = LoginResponse.builder().token(jwtToken)
                    .expiresIn(jwtService.getJwtExpiration()).build();

            return ResponseEntity.ok(loginResponse);

        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid credentials: " + ex.getMessage());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + ex.getMessage());
        }
    }
}
