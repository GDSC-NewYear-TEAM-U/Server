package com.gdsc.colot.config;

import com.gdsc.colot.jwt.filter.JwtAuthenticationFilter;
import com.gdsc.colot.security.UserDetailsServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity // Spring Security 설정들을 활성화
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final UserDetailsServiceImpl userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    /*
        AuthenticationManager에서 authenticate 메소드를 실행할 때
        내부적으로 사용할 UserDetailsService와 PasswordEncoder를 설정
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and() // 세션 사용 X
                .csrf().disable() // csrf 보호 비활성화
                .cors().disable() // cors 보호 비활성화
                .formLogin().disable() // 기본 폼 로그인 비활성화
                .logout().disable() // 로그아웃 비활성화 ('/logout' URI를 사용하기 위한 설정)
                .httpBasic().disable() // HTTP Basic 인증 비활성화(지금 사용 안 함)
                .authorizeRequests() // URL 별 권한 관리를 설정하는 옵션의 시작점
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()// OPTIONS 메소드는 모두 허용
                .antMatchers(
                        "/api/v1/**",
                        "/api/v2/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-resources/**",
                        "/v2/api-docs",
                        "/profile",
                        "/"
                ).permitAll()
                .antMatchers("/api/v1/oauth2/**").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/authorize","/api/v1/users").anonymous()
                .anyRequest().authenticated().and() // 나머지 URI는 인증 필요
                .exceptionHandling()
                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)); // 인증 실패시 401 응답

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

