package com.gdsc.colot.controller.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @Size(min = 1, max = 20, message = "이름이 입력되지 않았거나 너무 긴 이름입니다")
    private String name;

    @NotBlank(message = "이메일을 입력해주세요")
    @Email(message = "이메일 형식이 잘못되었습니다")
    private String email;

    @Pattern(regexp = "[a-zA-z!@#$%^&*-_]{6,20}", message = "6~20 길이의 알파벳과 숫자, 특수문자만 사용할 수 있습니다")
    private String password;

}
