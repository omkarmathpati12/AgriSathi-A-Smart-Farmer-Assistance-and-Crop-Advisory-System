package com.Auth_Service.Service;

import com.Auth_Service.Dto.AuthRequest;
import com.Auth_Service.Dto.AuthResponse;
import com.Auth_Service.Entity.AuthEntity;
import com.Auth_Service.Enums.Role;
import com.Auth_Service.Enums.Status;
import com.Auth_Service.Exceptions.ResourceNotFoundException;
import com.Auth_Service.Mapper.AuthMapper;
import com.Auth_Service.Repository.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepo authRepo;
    private final AuthMapper authMapper;

    public AuthResponse register(AuthRequest authRequest) {
        if(authRepo.existsByEmail(authRequest.email())){
            throw new ResourceNotFoundException("Email Already Exists",authRequest.email());
        }
        AuthEntity auth=authMapper.toRequest(authRequest);
        auth.setRole(Role.USER);
        auth.setStatus(Status.ACTIVE);
        AuthEntity saved=authRepo.save(auth);
        return authMapper.toResponse(saved);
    }

    public AuthResponse getById(Long authId) {

        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found",
                                authId.toString()
                        )
                );

        return authMapper.toResponse(auth);
    }


    public AuthResponse getByEmail(String email) {

        AuthEntity auth = authRepo.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found",
                                email
                        )
                );

        return authMapper.toResponse(auth);
    }


    public List<AuthResponse> getAllUsers() {

        return authRepo.findAll()
                .stream()
                .map(authMapper::toResponse)
                .toList();
    }


    public AuthResponse update(Long authId, AuthRequest authRequest) {

        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found",
                                authId.toString()
                        )
                );

        // Check if email is changed and already exists
        if (!auth.getEmail().equals(authRequest.email())
                && authRepo.existsByEmail(authRequest.email())) {

            throw new ResourceNotFoundException(
                    "Email Already Exists",
                    authRequest.email()
            );
        }

        authMapper.updateAuth(authRequest, auth);

        AuthEntity updated = authRepo.save(auth);

        return authMapper.toResponse(updated);
    }



    public void delete(Long authId) {

        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found",
                                authId.toString()
                        )
                );

        authRepo.delete(auth);
    }


    public AuthResponse activate(Long authId) {

        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User Not Found",
                                authId.toString()
                        )
                );

        auth.setStatus(Status.ACTIVE);

        return authMapper.toResponse(authRepo.save(auth));
    }


    public AuthResponse deactivate(Long authId) {

        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found",authId.toString()));

        auth.setStatus(Status.INACTIVE);

        return authMapper.toResponse(authRepo.save(auth));
    }


    public AuthResponse changeRole(Long authId, Role role) {

        AuthEntity auth = authRepo.findById(authId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found",authId.toString()));

        auth.setRole(role);

        return authMapper.toResponse(authRepo.save(auth));
    }
}
