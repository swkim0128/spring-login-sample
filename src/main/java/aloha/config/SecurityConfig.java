package aloha.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import aloha.common.CustomLoginSuccessHandler;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	private static Logger Log = LoggerFactory.getLogger(SecurityConfig.class);
	
	@Autowired
	DataSource dataSource;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// TODO Auto-generated method stub
		Log.info("security config......");
		
		// 로그인 설정
		http.formLogin()
		.loginPage("/auth/login")
		.loginProcessingUrl("login")
		.successHandler(createAuthenticationSeccessHandler());
		
		// 로그아웃 설정
		http.logout()
		.logoutUrl("/auth/logout")
		.invalidateHttpSession(true);
		
		// 예외 설정
		http.exceptionHandling(createAccessDeniedHandler());
		
		// 자동 로그인
		http.rememberMe()
		.key("aloha")
		.tokenRepository(createJDBCRepository())
		.tokenValiditySeconds(60 * 60 * 24);
	}
	
	@Bean
	public AuthenticationSuccessHandler createAuthenticationSeccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	@Bean
	public AccessDeniedHandler createAccessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}
	
	@Bean
	public PersistentTokenRepository createJDBCRepository() {
		JdbcTokenRepositoryImpl repo = new JdbcTokenRepositoryImpl();
		repo.setDataSource(dataSource);
		return repo;
	}
	
	@Bean
	public UserDetailsService createUserDetailService() {
		return new CustomUserDetailsService();
	}
	
	@Bean
	protected PasswordEncoder createPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// TODO Auto-generated method stub
		auth.userDetailsService(createUserDetailService())
		.passwordEncoder(createPasswordEncoder());
	}
	
	
}
