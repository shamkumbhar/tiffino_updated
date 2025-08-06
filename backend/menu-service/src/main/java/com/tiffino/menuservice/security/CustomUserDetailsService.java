//package com.tiffino.menuservice.security;
//
//import org.springframework.security.core.userdetails.*;
//import org.springframework.stereotype.Service;
//import java.util.Collections;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Override
//    public UserDetails loadUserByUsername(String username) {
//        // In real use case, fetch user from DB
//        return User.withUsername(username)
//                .password("{noop}password") // NoOpPasswordEncoder for testing
//                .authorities("USER")
//                .build();
//    }
//}
