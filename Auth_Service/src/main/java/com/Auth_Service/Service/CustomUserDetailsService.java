package com.Auth_Service.Service;

import com.Auth_Service.Entity.AuthEntity;
import com.Auth_Service.Repository.AuthRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthRepo authRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AuthEntity auth=authRepo.findByEmail(email)
                .orElseThrow(()->new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User
                .withUsername(auth.getEmail())
                .password(auth.getPassword())
                .roles(String.valueOf(auth.getRole()))
                .disabled(auth.getStatus() != com.Auth_Service.Enums.Status.ACTIVE)
                .build();
    }
}
