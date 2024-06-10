package com.zut.znajdzmiejsce.controller;

import com.zut.znajdzmiejsce.dto.LoginRequestDTO;
import com.zut.znajdzmiejsce.dto.RegistrationRequestDTO;
import com.zut.znajdzmiejsce.dto.RegistrationResponseDTO;
import com.zut.znajdzmiejsce.exception.ApiErrorResponse;
import com.zut.znajdzmiejsce.exception.AuthenticationFailedException;
import com.zut.znajdzmiejsce.exception.RegistrationException;
import com.zut.znajdzmiejsce.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authenticate")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/user")
    public ResponseEntity<?> authenticateUser(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        try {
            String token = authService.authenticate(loginRequestDTO, "ROLE_USER");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok().headers(headers).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationException", e.getMessage()));
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationFailedException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }

    @PostMapping("/developer")
    public ResponseEntity<?> authenticateDeveloper(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        try {
            String token = authService.authenticate(loginRequestDTO, "ROLE_DEVELOPER");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok().headers(headers).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationException", e.getMessage()));
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationFailedException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }

    @PostMapping("/administrator")
    public ResponseEntity<?> authenticateAdministrator(
            @Valid @RequestBody LoginRequestDTO loginRequestDTO
    ) {
        try {
            String token = authService.authenticate(loginRequestDTO, "ROLE_ADMINISTRATOR");

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + token);

            return ResponseEntity.ok().headers(headers).build();
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationException", e.getMessage()));
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiErrorResponse("AuthenticationFailedException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }
}