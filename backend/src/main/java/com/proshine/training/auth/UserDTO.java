package com.proshine.training.auth;

import com.proshine.training.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String phoneNumber;
    private String idCardNumber;

    public static UserDTO from(User user) {
        return UserDTO.builder()
                .id(user.getId() != null ? user.getId().toString() : null)
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .idCardNumber(user.getIdCardNumber())
                .build();
    }
}

