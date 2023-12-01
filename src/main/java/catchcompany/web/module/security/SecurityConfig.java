package catchcompany.web.module.security;

import static org.springframework.security.config.Customizer.*;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((req) -> req
				.dispatcherTypeMatchers(DispatcherType.FORWARD).permitAll()
				.anyRequest().permitAll()
			)
			.formLogin(login -> login    // form 방식 로그인 사용
				.defaultSuccessUrl("/view/home", true)    // 성공 시 dashboard로
				.loginProcessingUrl("/login")    // [B] submit 받을 url
				.usernameParameter("id")    // [C] submit할 아이디
				.passwordParameter("password")    // [D] submit할 비밀번호
				.permitAll()
			)
			.logout(withDefaults())
			.csrf(csrf -> csrf.disable());

		// https://docs.spring.io/spring-security/reference/features/exploits/headers.html#headers-xss-protection
		return http.build();
	}


}
