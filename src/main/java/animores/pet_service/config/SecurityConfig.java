package animores.pet_service.config;

import animores.pet_service.security.AuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthenticationFilter authenticationFilter;

    String[] allowedUrls = {"/", "/swagger-ui/**", "/v3/api-docs", "/v3/api-docs/**", "/error"};

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))	// H2 콘솔 사용을 위한 설정
                .authorizeHttpRequests(requests ->
                        requests.requestMatchers(PathRequest.toH2Console()).permitAll()	// H2 콘솔 접속은 모두에게 허용
                                .requestMatchers(allowedUrls).permitAll()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
                                .anyRequest().authenticated()	// requestMatchers의 인자로 전달된 url은 모두에게 허용
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilterBefore(authenticationFilter, BasicAuthenticationFilter.class)
                .build();
    }

}
