//package com.Crop_Service.Config;
//
//import feign.RequestInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
//
//@Configuration
//public class FeignConfig {
//
//    @Bean
//    public RequestInterceptor jwtInterceptor() {
//
//        return requestTemplate -> {
//
//            Authentication authentication =
//                    SecurityContextHolder
//                            .getContext()
//                            .getAuthentication();
//
//            System.out.println("Feign Authentication = " + authentication);
//
//            if (authentication instanceof JwtAuthenticationToken jwtAuth) {
//
//                String token = jwtAuth.getToken().getTokenValue();
//
//                System.out.println("🔥 JWT FOUND IN FEIGN");
//                System.out.println("Token length = " + token.length());
//
//                requestTemplate.header(
//                        "Authorization",
//                        "Bearer " + token
//                );
//
//            } else {
//                System.out.println("❌ NO JwtAuthenticationToken FOUND");
//            }
//        };
//    }
//}