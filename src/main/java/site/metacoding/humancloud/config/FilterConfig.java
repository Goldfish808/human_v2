package site.metacoding.humancloud.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import site.metacoding.humancloud.config.auth.JwtAuthenticationFilter;
import site.metacoding.humancloud.config.auth.JwtAuthorizationFilter;
import site.metacoding.humancloud.domain.user.UserDao;
import site.metacoding.humancloud.util.SHA256;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class FilterConfig {

    private final UserDao userDao; // DI (스프링 IoC 컨테이너에서 옴)
    private final SHA256 sha256;

    // IoC등록 (서버 실행시)
    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilterRegister() {
        FilterRegistrationBean<JwtAuthenticationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthenticationFilter(userDao, sha256));
        bean.addUrlPatterns("/login");
        bean.setOrder(1); // 낮은 순서대로 실행
        return bean;
    }

    @Profile("prod")
    @Bean
    public FilterRegistrationBean<JwtAuthorizationFilter> jwtAuthorizationFilterRegister() {
        log.debug("디버그 : 인가 필터 등록");
        FilterRegistrationBean<JwtAuthorizationFilter> bean = new FilterRegistrationBean<>(
                new JwtAuthorizationFilter());
        bean.addUrlPatterns("/s/*"); // 원래 두개인데, 이 친구만 예외
        bean.setOrder(2);
        return bean;
    }

}
