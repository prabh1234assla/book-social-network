package webly.bookstore.backend.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import webly.bookstore.backend.DTOs.LogindDTO;
import webly.bookstore.backend.DTOs.RegisterDTO;
import webly.bookstore.backend.Models.LoginResponse;
import webly.bookstore.backend.Models.User;
import webly.bookstore.backend.Service.AuthenticationService;
import webly.bookstore.backend.Service.JwtService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JwtService jwtService;

    private final AuthenticationService authenticationService;

    public AuthenticationController(
        JwtService jwtService,
        AuthenticationService authenticationService
    ){
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> Register(@RequestBody RegisterDTO registerDTO){
        User registeredUser = authenticationService.Signup(registerDTO);

        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> Authenticate(@RequestBody LogindDTO logindDTO){
        User authenticatedUser = authenticationService.Authenticate(logindDTO);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = LoginResponse.builder().
                                                    token(jwtToken).
                                                    expiresIn(jwtService.getJwtExpiration()).
                                                    build();

        return ResponseEntity.ok(loginResponse);
    }
}
