package com.proshine.training.auth;

import com.proshine.training.user.User;
import com.proshine.training.user.UserRepository;
import com.proshine.training.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final JwtUtil jwtUtil;

    public LoginResponse login(String username, String password) {
        User user = userRepository.findByUsername(username);
        if (user == null || !passwordService.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        String token = jwtUtil.generateToken(user.getUsername());
        UserDTO dto = UserDTO.from(user);
        return LoginResponse.builder()
                .token(token)
                .user(dto)
                .build();
    }
}

