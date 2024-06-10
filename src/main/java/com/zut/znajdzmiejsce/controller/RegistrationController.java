package com.zut.znajdzmiejsce.controller;

import com.zut.znajdzmiejsce.dto.RegistrationRequestDTO;
import com.zut.znajdzmiejsce.dto.RegistrationResponseDTO;
import com.zut.znajdzmiejsce.exception.ApiErrorResponse;
import com.zut.znajdzmiejsce.exception.RegistrationException;
import com.zut.znajdzmiejsce.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/register")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/user")
    public ResponseEntity<?> registerUser(
            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO
    ) {
        try {
            RegistrationResponseDTO newUserSavedResponseDTO = registrationService.register(registrationRequestDTO, "ROLE_USER");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newUserSavedResponseDTO);
        } catch (RegistrationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse("RegistrationException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }

    @PostMapping("/developer")
    public ResponseEntity<?> registerDeveloper(
            @Valid @RequestBody RegistrationRequestDTO registrationRequestDTO
    ) {
        try {
            RegistrationResponseDTO newUserSavedResponseDTO = registrationService.register(registrationRequestDTO, "ROLE_DEVELOPER");
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(newUserSavedResponseDTO);
        } catch (RegistrationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiErrorResponse("RegistrationException", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiErrorResponse("InternalServerError", "Unexpected internal server error occurred."));
        }
    }
}