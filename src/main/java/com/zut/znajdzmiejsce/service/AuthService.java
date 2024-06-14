package com.zut.znajdzmiejsce.service;

import com.zut.znajdzmiejsce.dto.LoginRequestDTO;
import com.zut.znajdzmiejsce.dto.RegistrationRequestDTO;
import com.zut.znajdzmiejsce.dto.RegistrationResponseDTO;
import com.zut.znajdzmiejsce.exception.AuthenticationFailedException;
import com.zut.znajdzmiejsce.exception.RegistrationException;
import com.zut.znajdzmiejsce.security.jwt.JwtService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    public String authenticate(LoginRequestDTO loginRequestDTO, String roleName) throws AuthenticationException {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.getEmail() + ":" + roleName,
                            loginRequestDTO.getPassword()
                    )
            );

            UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequestDTO.getEmail() + ":" + roleName);

            return jwtService.generateToken(userDetails, roleName);
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Invalid username or password");
        } catch (LockedException e) {
            throw new AuthenticationFailedException("Account locked. Please contact support.");
        } catch (Exception e) {
            throw new AuthenticationFailedException("Login failed: " + e.getMessage(), e);
        }
    }
}