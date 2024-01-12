package com.gdsc.colot.controller.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public final class OAuth2AuthorizationResponseDto {

    private String state;
    private String code;
    private String error;

    public OAuth2AuthorizationResponseDto() {
    }

    public OAuth2AuthorizationResponseDto(String state, String code, String error) {
        this.state = state;
        this.code = code;
        this.error = error;
    }
}
