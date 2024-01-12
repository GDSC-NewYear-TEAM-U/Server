package com.gdsc.colot.domain;

import com.gdsc.colot.controller.dto.OAuth2AccountDto;
import com.gdsc.colot.domain.user.User;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "OAUTH_ACCOUNT", uniqueConstraints = {@UniqueConstraint(columnNames = {"provider", "providerId"})})
public class OAuth2Account extends BaseEntity {

    private String providerId;
    private String provider;
    private String token;
    private String refreshToken;
    private LocalDateTime tokenExpiredAt;
    @OneToOne(mappedBy = "social")
    private User user;

    @Builder
    public OAuth2Account(String providerId, String provider, String token, String refreshToken, LocalDateTime tokenExpiredAt) {
        this.providerId = providerId;
        this.provider = provider;
        this.token = token;
        this.refreshToken = refreshToken;
        this.tokenExpiredAt = tokenExpiredAt;
    }

    public void updateToken(String token, String refreshToken, LocalDateTime tokenExpiredAt) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.tokenExpiredAt = tokenExpiredAt;
    }

    public void linkUser(User user) {
        this.user = user;
    }

    public void unlinkUser() {
        this.user = null;
    }

    public OAuth2AccountDto toDTO() {
        return OAuth2AccountDto.builder()
                .provider(provider)
                .providerId(providerId)
                .createAt(getCreateAt())
                .token(token)
                .refreshToken(refreshToken)
                .tokenExpiredAt(tokenExpiredAt).build();
    }
}
