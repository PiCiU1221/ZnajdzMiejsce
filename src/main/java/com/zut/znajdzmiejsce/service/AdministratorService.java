package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.repository.RoleRepository;
import com.zut.znajdzmiejsce.repository.UserRepository;
import com.zut.znajdzmiejsce.security.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.zut.znajdzmiejsce.model.User;

@Service
@RequiredArgsConstructor
public class AdministratorService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAdmin(String email, String password) {
        Role role = roleRepository.findByName("ROLE_ADMINISTRATOR").orElseThrow(() -> new RuntimeException("Role not found"));

        if (userRepository.existsByEmailAndRole(email, role)) {
            throw new RuntimeException("Administrator account already exists");
        }
        
        User admin = new User();
        admin.setEmail(email);
        admin.setPassword(passwordEncoder.encode(password));
        admin.setRole(role);

        userRepository.save(admin);
    }
}
