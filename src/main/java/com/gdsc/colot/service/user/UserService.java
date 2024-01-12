package com.gdsc.colot.service.user;


import com.gdsc.colot.controller.dto.request.SignUpRequestDto;
import com.gdsc.colot.oauth2.OAuth2Token;
import com.gdsc.colot.oauth2.userInfo.OAuth2UserInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    void saveUser(SignUpRequestDto signUpRequestDto);

    UserDetails loginOAuth2User(String provider, OAuth2Token oAuth2Token, OAuth2UserInfo userInfo);
}
