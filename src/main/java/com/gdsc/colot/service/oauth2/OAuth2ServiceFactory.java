package com.gdsc.colot.service.oauth2;

import com.gdsc.colot.exception.ErrorCode;
import com.gdsc.colot.exception.model.OAuth2NotSupportException;
import org.springframework.web.client.RestTemplate;

public class OAuth2ServiceFactory {

    public static OAuth2Service getOAuth2Service(RestTemplate restTemplate, String registrationId) {
        if (registrationId.equalsIgnoreCase("google"))
            return new GoogleOAuth2Service(restTemplate);
        if (registrationId.equalsIgnoreCase("kakao"))
            return new KakaoOAuth2Service(restTemplate);
        else
            throw new OAuth2NotSupportException(ErrorCode.OAUTH2_NOT_SUPPORT_EXCEPTION, ErrorCode.OAUTH2_NOT_SUPPORT_EXCEPTION.getMessage());
    }

}
