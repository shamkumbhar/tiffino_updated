package com.tiffino.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private Long id;
    private String username;
    private String passwordHash;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private Set<String> roles;
    private LocalDateTime dateJoined;
    private LocalDateTime lastLogin;
    private Boolean isActive;
    private String profileImageUrl;
    private Set<String> dietaryPreferences;
    private Set<String> allergenPreferences;









}
