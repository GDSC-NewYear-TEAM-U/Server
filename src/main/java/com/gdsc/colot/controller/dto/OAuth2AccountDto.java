package com.gdsc.colot.controller.dto;

import com.gdsc.colot.oauth2.OAuth2Token;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class OAuth2AccountDto {
    private String provider;
    private String providerId;
    private LocalDateTime createAt;
    private OAuth2Token oAuth2Token;

    @Builder
    public OAuth2AccountDto(String provider, String providerId, LocalDateTime createAt, String token, String refreshToken, LocalDateTime tokenExpiredAt) {
        this.provider = provider;
        this.providerId = providerId;
        this.createAt = createAt;
        this.oAuth2Token = new OAuth2Token(token, refreshToken, tokenExpiredAt);
    }
}
