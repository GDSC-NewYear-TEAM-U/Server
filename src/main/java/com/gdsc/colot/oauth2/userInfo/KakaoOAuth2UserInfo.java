package com.gdsc.colot.oauth2.userInfo;

import java.util.Map;

public class KakaoOAuth2UserInfo extends OAuth2UserInfo {

    public KakaoOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return String.valueOf(attributes.get("id"));
    }

    @Override
    public String getName() {
        return (String) parsingProfile().get("nickname");
    }

    @Override
    public String getEmail() {
        return (String) parsingProfile().get("email");
    }

    private Map<String, Object> parsingProperties() {
        return (Map<String, Object>) attributes.get("kakao_account");
    }

    private Map<String, Object> parsingProfile() {
        return (Map<String, Object>) parsingProperties().get("profile");
    }

}
