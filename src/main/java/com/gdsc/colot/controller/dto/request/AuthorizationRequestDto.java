package com.gdsc.colot.controller.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorizationRequestDto {

    @NotBlank(message = "이메일을 입력하세요")
    private String username;

    @NotBlank(message = "패스워드를 입력하세요")
    private String password;

}
