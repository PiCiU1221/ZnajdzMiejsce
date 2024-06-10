package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.dto.RegistrationRequestDTO;
import com.zut.znajdzmiejsce.dto.RegistrationResponseDTO;
import com.zut.znajdzmiejsce.exception.RegistrationException;
import com.zut.znajdzmiejsce.model.User;
import com.zut.znajdzmiejsce.repository.RoleRepository;
import com.zut.znajdzmiejsce.repository.UserRepository;
import com.zut.znajdzmiejsce.security.model.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public RegistrationResponseDTO register(RegistrationRequestDTO registrationRequestDTO, String roleName) throws RegistrationException, ConstraintViolationException {
        Role role = roleRepository.findByName(roleName).orElseThrow(() -> new RuntimeException("Role not found"));

        if (userRepository.existsByEmailAndRole(registrationRequestDTO.getEmail(), role)) {
            throw new RegistrationException("Email is already taken");
        }

        User user = new User();
        user.setEmail(registrationRequestDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequestDTO.getPassword()));
        user.setRole(role);

        return new RegistrationResponseDTO(userRepository.save(user));
    }
}
