package com.gdsc.colot.oauth2;

import com.gdsc.colot.controller.dto.request.OAuth2AuthorizationRequestDto;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryOAuth2RequestRepository {

    private Map<String, OAuth2AuthorizationRequestDto> oAuth2RequestMap = new HashMap<>();

    public void saveOAuth2Request(String state, OAuth2AuthorizationRequestDto oAuth2AuthorizationRequestDto) {
        oAuth2RequestMap.put(state, oAuth2AuthorizationRequestDto);
    }

    public OAuth2AuthorizationRequestDto getOAuth2Request(String state) {
        return oAuth2RequestMap.get(state);
    }

    public OAuth2AuthorizationRequestDto deleteOAuth2Request(String state) {
        return oAuth2RequestMap.remove(state);
    }

}
