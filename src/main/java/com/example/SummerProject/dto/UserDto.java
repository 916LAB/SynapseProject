package com.example.SummerProject.dto;

import com.example.SummerProject.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank
    @Email
    private String id;

    @NotBlank
    private String pw;

    @NotBlank
    private String nickname;

    public User toEntity() {
        return User.builder()
                .id(id)
                .pw(pw)
                .nickname(nickname)
                .build();
    }

}

