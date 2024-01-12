package com.gdsc.colot.controller;

import com.gdsc.colot.common.dto.BaseResponse;
import com.gdsc.colot.controller.dto.request.SignUpRequestDto;
import com.gdsc.colot.exception.SuccessCode;
import com.gdsc.colot.security.UserDetailsImpl;
import com.gdsc.colot.service.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse signUpNewUser(@RequestBody @Valid SignUpRequestDto signUpRequestDto) {
        userService.saveUser(signUpRequestDto);
        return BaseResponse.success(SuccessCode.SIGNUP_SUCCESS);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse<String> signInTest(@AuthenticationPrincipal UserDetailsImpl loginUser) {
        return BaseResponse.success(SuccessCode.LOGIN_SUCCESS, loginUser.getEmail());
    }

}
