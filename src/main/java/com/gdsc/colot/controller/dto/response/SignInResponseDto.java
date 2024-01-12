package com.gdsc.colot.controller.dto.response;

import com.gdsc.colot.domain.user.UserType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SignInResponseDto {

    private String name;
    private UserType userType;
    private String accessToken;

    public static SignInResponseDto of(String name, UserType userType, String accessToken) {
        return new SignInResponseDto(name, userType, accessToken);
    }

}
