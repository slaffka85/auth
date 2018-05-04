package com.egartech;

import com.egartech.auth.LoginSuccessHandler;
import com.egartech.boot.autoconfigure.AuthenticationConfigurer;
import com.egartech.security.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import java.util.Collections;

@Configuration
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {
    // При старте приложения стартер создаёт экземпляры этих классов и они
    // автоматически инжектятся в SecurityConfigurer
    private final SecurityService securityService;
    private final AuthenticationConfigurer authConfigurer;

    @Autowired
    public SecurityConfigurer(SecurityService securityService, AuthenticationConfigurer configurer) {
        this.securityService = securityService;
        this.authConfigurer = configurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Настройка HttpSecurity
        // В качестве обработчика успешного логина передаётся экземпляр
        // LoginSuccessHandler, который при успешном логине создаёт посредством SecurityService пользователя и / или
        // выполняет другие действия специфичные для приложения
        http
                .authorizeRequests()
                // ...
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .successHandler(new LoginSuccessHandler(securityService));

        // ...
    }

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        authConfigurer.configure(authManagerBuilder, userDetailsService());
    }

    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        AuthenticationProvider provider = authConfigurer.createAuthenticationProvider();
        return provider == null ? super.authenticationManager() : new ProviderManager(Collections.singletonList(provider));
    }
}
