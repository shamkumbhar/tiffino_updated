package com.tiffino.orderservice.external;

import com.tiffino.orderservice.dto.UserDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Set;

@Component
public class Userclient {


    public UserDto getUserById(Long userId) {
        if (userId == 1L || userId == 12L) {  // id quals 1 0o 12 it enter block

            return UserDto.builder()      // constrct userdto with hardcoded
                    .id(1L)
                    .username("sachin" + userId)
                    .email("sachin@gmail.com")
                    .phoneNumber("9876543210" + userId)
                    .firstName("sachin")
                    .lastName("shete")
                    .roles(Set.of("USER"))
                    .dateJoined(LocalDateTime.now().minusMonths(1))
                    .lastLogin(LocalDateTime.now().minusDays(1))
                    .isActive(true)
                    .profileImageUrl("https://example.com/profile.jpg")
                    .dietaryPreferences(Set.of("Vegetarian"))
                    .allergenPreferences(Set.of("Nut-free"))
                    .build();
        } else if (userId == 2L) {
            return UserDto.builder()
                    .id(2L)
                    .username("shubham")
                    .email("sshubham@123")
                    .phoneNumber("992289")
                    .firstName("shubham")
                    .lastName("shete")
                    .roles(Set.of("USER"))
                    .dateJoined(LocalDateTime.now().minusMonths(2))
                    .lastLogin(LocalDateTime.now().minusDays(2))
                    .isActive(true)
                    .profileImageUrl("https://example.com/default.jpg")
                    .dietaryPreferences(Set.of("vegan"))
                    .allergenPreferences(Set.of("dairy-free"))
                    .build();
        }
        else if (userId == 12L) {
            return UserDto.builder()
                    .id(12L)
                    .username("akash")
                    .email("akash007@example.com")
                    .phoneNumber("987650007")
                    .firstName("Akash")
                    .lastName("Patil")
                    .roles(Set.of("ADMIN", "USER"))
                    .dateJoined(LocalDateTime.now().minusMonths(3))
                    .lastLogin(LocalDateTime.now().minusDays(5))
                    .isActive(true)
                    .profileImageUrl("https://example.com/akash.jpg")
                    .dietaryPreferences(Set.of("keto"))
                    .allergenPreferences(Set.of("gluten-free"))
                    .build();
        }

        else {
            return UserDto.builder()
                    .id(userId)
                    .username("Unknown")
                    .email("N/A")
                    .phoneNumber("N/A")
                    .firstName("N/A")
                    .lastName("N/A")
                    .roles(Set.of())
                    .dateJoined(null)
                    .lastLogin(null)
                    .isActive(false)
                    .profileImageUrl("https://example.com/default.jpg")
                    .dietaryPreferences(Set.of())
                    .allergenPreferences(Set.of())
                    .build();
        }
    }


}
