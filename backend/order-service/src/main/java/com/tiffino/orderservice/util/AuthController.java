//package com.tiffino.orderservice.util;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    @Autowired
//    private JwtToken jwtUtil;
//
//    @PostMapping("/login")
//    public String login(@RequestBody Map<String, String> data) {
//        String username = data.get("username");
//        String password = data.get("password");
//
//        if ("admin".equals(username) && "password".equals(password)) {
//            return jwtUtil.generateToken(username);
//        }
//        return "Invalid credentials";
//    }
//
//    @GetMapping("/validate")
//    public String validateToken(@RequestParam String token) {
//        if (jwtUtil.isTokenValid(token)) {
//            return "Valid token for user: " + jwtUtil.extractUsername(token);
//        }
//        return "Invalid token";
//    }
//
//
//
//
//
//
//}
