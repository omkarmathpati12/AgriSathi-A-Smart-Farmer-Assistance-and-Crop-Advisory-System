package com.Auth_Service.Service;

import com.Auth_Service.Dto.AuthRequest;
import com.Auth_Service.Dto.AuthResponse;
import com.Auth_Service.Dto.LoginRequest;
import com.Auth_Service.Entity.AuthEntity;
import com.Auth_Service.Enums.Role;
import com.Auth_Service.Enums.Status;
import com.Auth_Service.Exceptions.DuplicateResourceException;
import com.Auth_Service.Exceptions.ResourceNotFoundException;
import com.Auth_Service.Mapper.AuthMapper;
import com.Auth_Service.Repository.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepo authRepo;
    private final AuthMapper authMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Register new user — hashes password, sets default role USER and status ACTIVE
    public AuthResponse register(AuthRequest authRequest) {
        if (authRepo.existsByEmail(authRequest.email())) {
            throw new DuplicateResourceException("Email already exists: {}", authRequest.email());
        }
        AuthEntity auth = authMapper.toRequest(authRequest);
        auth.setPassword(passwordEncoder.encode(authRequest.password()));
        auth.setRole(Role.USER);
        auth.setStatus(Status.ACTIVE);
        AuthEntity saved = authRepo.save(auth);
        AuthResponse response = authMapper.toResponse(saved);
        return withToken(response, jwtService.generateToken(saved.getEmail(), saved.getRole().name()));
    }

    // Login — verifies password, returns user + JWT token
    public AuthResponse login(LoginRequest loginRequest) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.email(), loginRequest.password()));
        AuthEntity auth = authRepo.findByEmail(loginRequest.email())
                .orElseThrow(() -> new BadCredentialsException("Invalid email or password"));
        AuthResponse response = authMapper.toResponse(auth);
        return withToken(response, jwtService.generateToken(auth.getEmail(), auth.getRole().name()));
    }

    public AuthResponse getById(Long authId) {
        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", authId));
        return authMapper.toResponse(auth);
    }

    public AuthResponse getByEmail(String email) {
        AuthEntity auth = authRepo.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", email));
        return authMapper.toResponse(auth);
    }

    public List<AuthResponse> getAllUsers() {
        return authRepo.findAll().stream()
                .map(authMapper::toResponse)
                .toList();
    }

    public AuthResponse update(Long authId, AuthRequest authRequest) {
        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", authId));

        if (!auth.getEmail().equals(authRequest.email()) && authRepo.existsByEmail(authRequest.email())) {
            throw new DuplicateResourceException("Email already exists: {}", authRequest.email());
        }

        authMapper.updateAuth(authRequest, auth);
        // Re-hash password if it was changed
        auth.setPassword(passwordEncoder.encode(authRequest.password()));
        return authMapper.toResponse(authRepo.save(auth));
    }

    public void delete(Long authId) {
        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", authId));
        authRepo.delete(auth);
    }

    public AuthResponse activate(Long authId) {
        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", authId));
        auth.setStatus(Status.ACTIVE);
        return authMapper.toResponse(authRepo.save(auth));
    }

    public AuthResponse deactivate(Long authId) {
        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", authId));
        auth.setStatus(Status.INACTIVE);
        return authMapper.toResponse(authRepo.save(auth));
    }

    public AuthResponse changeRole(Long authId, Role role) {
        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: {}", authId));
        auth.setRole(role);
        return authMapper.toResponse(authRepo.save(auth));
    }

    // Helper: create a new AuthResponse with the token field set
    private AuthResponse withToken(AuthResponse response, String token) {
        return new AuthResponse(
                response.authId(),
                response.name(),
                response.email(),
                response.phone(),
                response.role(),
                response.status(),
                response.createdAt(),
                token
        );
    }
}
