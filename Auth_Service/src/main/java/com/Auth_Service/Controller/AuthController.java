package com.Auth_Service.Controller;

import com.Auth_Service.Dto.AuthRequest;
import com.Auth_Service.Enums.Role;
import com.Auth_Service.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(authRequest));
    }

    @GetMapping("/{authId}")
    public ResponseEntity<?> getById(@PathVariable Long authId) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getById(authId));
    }


    @GetMapping("/email/{email}")
    public ResponseEntity<?> getByEmail(@PathVariable String email) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getByEmail(email));
    }


    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.getAllUsers());
    }


    @PutMapping("/{authId}")
    public ResponseEntity<?> update(
            @PathVariable Long authId,
            @RequestBody AuthRequest authRequest) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.update(authId, authRequest));
    }



    @DeleteMapping("/{authId}")
    public ResponseEntity<?> delete(@PathVariable Long authId) {

        authService.delete(authId);

        return ResponseEntity
                .status(HttpStatus.NO_CONTENT)
                .build();
    }



    @PutMapping("/{authId}/activate")
    public ResponseEntity<?> activate(@PathVariable Long authId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.activate(authId));
    }


    @PutMapping("/{authId}/deactivate")
    public ResponseEntity<?> deactivate(@PathVariable Long authId) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.deactivate(authId));
    }


    @PutMapping("/{authId}/role")
    public ResponseEntity<?> changeRole(@PathVariable Long authId,@RequestParam Role role) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(authService.changeRole(authId, role));
    }
}
