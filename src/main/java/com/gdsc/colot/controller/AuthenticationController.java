package com.gdsc.colot.controller;

import com.gdsc.colot.common.dto.BaseResponse;
import com.gdsc.colot.controller.dto.request.AuthorizationRequestDto;
import com.gdsc.colot.controller.dto.response.OAuth2AuthorizationResponseDto;
import com.gdsc.colot.controller.dto.response.SignInResponseDto;
import com.gdsc.colot.exception.ErrorCode;
import com.gdsc.colot.exception.SuccessCode;
import com.gdsc.colot.exception.model.NotFoundException;
import com.gdsc.colot.exception.model.OAuth2RequestFailedException;
import com.gdsc.colot.jwt.JwtProvider;
import com.gdsc.colot.oauth2.ClientRegistration;
import com.gdsc.colot.oauth2.ClientRegistrationRepository;
import com.gdsc.colot.oauth2.OAuth2Token;
import com.gdsc.colot.oauth2.userInfo.OAuth2UserInfo;
import com.gdsc.colot.repository.UserRepository;
import com.gdsc.colot.service.oauth2.OAuth2Service;
import com.gdsc.colot.service.oauth2.OAuth2ServiceFactory;
import com.gdsc.colot.service.user.UserService;
import com.gdsc.colot.domain.user.User;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.math.BigInteger;
import java.security.SecureRandom;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final ClientRegistrationRepository clientRegistrationRepository;
    private final JwtProvider jwtProvider;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @ApiOperation(value = "일반 로그인", notes = "일반 로그인이 진행된다.")
    @PostMapping("/authorize")
    public BaseResponse<SignInResponseDto> authenticateUsernamePassword(@Valid @RequestBody AuthorizationRequestDto authorizationRequestDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authorizationRequestDto.getUsername(), authorizationRequestDto.getPassword()));
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
        final SignInResponseDto data = SignInResponseDto.of(
                user.getName(),
                user.getType(),
                jwtProvider.generateToken(userDetails.getUsername())
        );
        return BaseResponse.success(SuccessCode.LOGIN_SUCCESS, data);
    }

    @ApiOperation(value = "소셜 로그인", notes = "소셜 로그인이 진행된다.")
    @GetMapping("/oauth2/authorize/{provider}")
    public void redirectSocialAuthorizationPage(@PathVariable String provider, HttpServletRequest request, HttpServletResponse response) throws  Exception {
        String state = generateState();
        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider);
        OAuth2Service oAuth2Service = OAuth2ServiceFactory.getOAuth2Service(restTemplate, provider);
        oAuth2Service.redirectAuthorizePage(clientRegistration, state, response);
    }

    @ApiIgnore
    @RequestMapping("/oauth2/callback/{provider}")
    public BaseResponse<SignInResponseDto> oAuth2AuthenticationCallback(@PathVariable String provider, OAuth2AuthorizationResponseDto oAuth2AuthorizationResponseDto, HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (oAuth2AuthorizationResponseDto.getError() != null) {
            throw new OAuth2RequestFailedException(
                    ErrorCode.OAUTH2_REDIRECT_CALLBACK_EXCEPTION,
                    ErrorCode.OAUTH2_REDIRECT_CALLBACK_EXCEPTION.getMessage() + String.format("(%s [응답 코드: %s])", oAuth2AuthorizationResponseDto.getError(), oAuth2AuthorizationResponseDto.getCode())
            );
        }

        ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId(provider);
        OAuth2Service oAuth2Service = OAuth2ServiceFactory.getOAuth2Service(restTemplate, provider);

        OAuth2Token oAuth2Token = oAuth2Service.getAccessToken(clientRegistration, oAuth2AuthorizationResponseDto.getCode(), oAuth2AuthorizationResponseDto.getState());
        OAuth2UserInfo oAuth2UserInfo = oAuth2Service.getUserInfo(clientRegistration, oAuth2Token.getToken());

        UserDetails userDetails = userService.loginOAuth2User(provider, oAuth2Token, oAuth2UserInfo);
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_FOUND_USER_EXCEPTION, ErrorCode.NOT_FOUND_USER_EXCEPTION.getMessage()));
        final SignInResponseDto data = SignInResponseDto.of(
                user.getName(),
                user.getType(),
                jwtProvider.generateToken(userDetails.getUsername())
        );
        return BaseResponse.success(SuccessCode.LOGIN_SUCCESS, data);
    }

    private String generateState() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

}
