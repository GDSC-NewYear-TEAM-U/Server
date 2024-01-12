package com.gdsc.colot.domain.user;

import com.gdsc.colot.domain.BaseEntity;
import com.gdsc.colot.domain.OAuth2Account;
import com.gdsc.colot.security.AuthorityType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(nullable = false, length = 20)
    private String name;

    @Column(unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;

    @Enumerated(value = EnumType.STRING)
    private UserType type;

    @ElementCollection(targetClass = AuthorityType.class)
    @CollectionTable(name = "USER_AUTHORITY", joinColumns = @JoinColumn(name = "USER_ID"))
    @Enumerated(EnumType.STRING)
    private List<AuthorityType> authorities = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SOCIAL_ID")
    private OAuth2Account social;

    @Builder
    public User(String name, String email, String username, String password, UserType type) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.authorities.add(AuthorityType.ROLE_MEMBER);
        this.type = type;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities.stream()
                .map(authority -> new SimpleGrantedAuthority(authority.toString()))
                .collect(Collectors.toList());
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateEmail(String email) {
        this.email = email;
        if (type.equals(UserType.DEFAULT))
            this.username = email;
    }

    public void linkSocial(OAuth2Account oAuth2Account) {
        this.social = oAuth2Account;
        oAuth2Account.linkUser(this);
    }

    public void unlinkSocial() {
        this.social.unlinkUser();
        this.social = null;
    }
}
