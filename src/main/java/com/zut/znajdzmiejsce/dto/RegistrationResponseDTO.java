package com.zut.znajdzmiejsce.dto;

import com.zut.znajdzmiejsce.model.User;
import lombok.Data;

@Data
public class RegistrationResponseDTO {
    private Long userId;
    private String email;

    public RegistrationResponseDTO(User user) {
        this.userId = user.getUserId();
        this.email = user.getEmail();
    }
}