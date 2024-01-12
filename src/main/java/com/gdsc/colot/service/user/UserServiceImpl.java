package com.gdsc.colot.service.user;

import com.gdsc.colot.controller.dto.request.SignUpRequestDto;
import com.gdsc.colot.domain.OAuth2Account;
import com.gdsc.colot.domain.user.User;
import com.gdsc.colot.domain.user.UserType;
import com.gdsc.colot.exception.ErrorCode;
import com.gdsc.colot.exception.model.DuplicateUserException;
import com.gdsc.colot.oauth2.OAuth2Token;
import com.gdsc.colot.oauth2.userInfo.OAuth2UserInfo;
import com.gdsc.colot.repository.OAuth2AccountRepository;
import com.gdsc.colot.repository.UserRepository;
import com.gdsc.colot.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OAuth2AccountRepository oAuth2AccountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(SignUpRequestDto signUpRequestDto) {
        checkDuplicateEmail(signUpRequestDto.getEmail());
        User user = User.builder()
                .username(signUpRequestDto.getEmail())
                .name(signUpRequestDto.getName())
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .type(UserType.DEFAULT)
                .build();

        userRepository.save(user);
    }

    @Override
    public UserDetails loginOAuth2User(String provider, OAuth2Token oAuth2Token, OAuth2UserInfo userInfo) {
        Optional<OAuth2Account> optOAuth2Account = oAuth2AccountRepository.findByProviderAndProviderId(provider, userInfo.getId());
        User user = null;

        if (optOAuth2Account.isPresent()) {
            OAuth2Account oAuth2Account = optOAuth2Account.get();
            user = oAuth2Account.getUser();
            if (oAuth2Token.getRefreshToken() == null) {
                oAuth2Account.updateToken(oAuth2Token.getToken(), oAuth2Account.getRefreshToken(), oAuth2Token.getExpiredAt());
            } else {
                oAuth2Account.updateToken(oAuth2Token.getToken(), oAuth2Token.getRefreshToken(), oAuth2Token.getExpiredAt());
            }
        } else {
            OAuth2Account newAccount = OAuth2Account.builder()
                    .provider(provider)
                    .providerId(userInfo.getId())
                    .token(oAuth2Token.getToken())
                    .refreshToken(oAuth2Token.getRefreshToken())
                    .tokenExpiredAt(oAuth2Token.getExpiredAt()).build();
            oAuth2AccountRepository.save(newAccount);

            if (userInfo.getEmail() != null) {
                user = userRepository.findByEmail(userInfo.getEmail())
                        .orElse(User.builder()
                                .username(provider + "_" + userInfo.getId())
                                .name(userInfo.getName())
                                .email(userInfo.getEmail())
                                .type(UserType.OAUTH)
                                .build());
            } else {
                user = User.builder()
                        .username(provider + "_" + userInfo.getId())
                        .name(userInfo.getName())
                        .email(userInfo.getName() + "@kakao.com")
                        .type(UserType.OAUTH)
                        .build();
            }

            if (user.getId() == null) {
                userRepository.save(user);
            }

            user.linkSocial(newAccount);
        }

        return UserDetailsImpl.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .type(user.getType())
                .authorities(user.getAuthorities()).build();
    }

    // 이메일 중복 체크 기능
    private void checkDuplicateEmail(String email) {
        if (userRepository.existsByEmail(email))
            throw new DuplicateUserException(ErrorCode.DUPLICATE_EMAIL_EXCEPTION, ErrorCode.DUPLICATE_EMAIL_EXCEPTION.getMessage());
    }

}
