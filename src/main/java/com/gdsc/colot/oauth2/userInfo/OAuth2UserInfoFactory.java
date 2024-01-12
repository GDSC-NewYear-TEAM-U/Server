package com.gdsc.colot.oauth2.userInfo;

import com.gdsc.colot.exception.ErrorCode;
import com.gdsc.colot.exception.model.OAuth2NotSupportException;

import java.util.Map;

public class OAuth2UserInfoFactory {

    public static OAuth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if(registrationId.equalsIgnoreCase("google")) {
            return new GoogleOAuth2UserInfo(attributes);
        } else if (registrationId.equalsIgnoreCase("kakao")) {
            return new KakaoOAuth2UserInfo(attributes);
        } else {
            throw new OAuth2NotSupportException(ErrorCode.OAUTH2_NOT_SUPPORT_EXCEPTION, ErrorCode.OAUTH2_NOT_SUPPORT_EXCEPTION.getMessage());
        }
    }

}
