package com.Auth_Service.Controller;

import com.Auth_Service.Dto.AuthRequest;
import com.Auth_Service.Dto.AuthResponse;
import com.Auth_Service.Dto.LoginRequest;
import com.Auth_Service.Enums.Role;
import com.Auth_Service.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @GetMapping("/{authId}")
    public ResponseEntity<AuthResponse> getById(@PathVariable Long authId) {
        return ResponseEntity.ok(authService.getById(authId));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AuthResponse> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(authService.getByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<AuthResponse>> getAllUsers() {
        return ResponseEntity.ok(authService.getAllUsers());
    }

    @PutMapping("/{authId}")
    public ResponseEntity<AuthResponse> update(@PathVariable Long authId, @RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.update(authId, authRequest));
    }

    @DeleteMapping("/{authId}")
    public ResponseEntity<Void> delete(@PathVariable Long authId) {
        authService.delete(authId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{authId}/activate")
    public ResponseEntity<AuthResponse> activate(@PathVariable Long authId) {
        return ResponseEntity.ok(authService.activate(authId));
    }

    @PutMapping("/{authId}/deactivate")
    public ResponseEntity<AuthResponse> deactivate(@PathVariable Long authId) {
        return ResponseEntity.ok(authService.deactivate(authId));
    }

    @PutMapping("/{authId}/role")
    public ResponseEntity<AuthResponse> changeRole(@PathVariable Long authId, @RequestParam Role role) {
        return ResponseEntity.ok(authService.changeRole(authId, role));
    }
}
