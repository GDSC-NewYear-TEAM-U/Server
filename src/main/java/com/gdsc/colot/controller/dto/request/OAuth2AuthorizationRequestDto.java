package com.gdsc.colot.controller.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OAuth2AuthorizationRequestDto {

    private String referer;
    private String redirectUri;
    private String callback;

    @Builder
    public OAuth2AuthorizationRequestDto(String referer, String redirectUri, String callback) {
        this.referer = referer;
        this.redirectUri = redirectUri;
        this.callback = callback;
    }
}
