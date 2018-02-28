package com.fxlabs.fxt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

/**
 * @author Intesar Shannan Mohammed
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableJdbcHttpSession(maxInactiveIntervalInSeconds = 604800)
//@EnableWebMvc
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private FxUserDetailsService userDetailsService;

    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/api/v1/users/personal-sign-up",
                        "/api/v1/users/team-sign-up",
                        "/api/v1/users/enterprise-sign-up",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html**",
                        "/webjars/**",
                        "/monitoring").permitAll() // sign-ups
                .antMatchers("/style/**",
                        "/font-awesome/**",
                        "/access.html",
                        "/favicon.png",
                        "/fx-white-100x100.png",
                        "/favicon.ico").permitAll() // login css

                .requestMatchers(EndpointRequest.toAnyEndpoint()).hasAnyRole("SUPER_USER") //Actuator
                .anyRequest().authenticated()
                    .and()
                    .csrf().disable()
                .formLogin()
                    .loginPage("/access.html")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/")
                    .and()
                .httpBasic()
                    .and()
                .logout();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider
                = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(encoder());

        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder(11);
    }

    /*@Bean
    public TextEncryptor encryptor() {
        return Encryptors.text("password", "66782d70617373776f7264"); //fx-password
    }*/

    /*@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
                .withUser("admin").password("admin123").roles("USER", "SUPERUSER"); // ... etc.
    }*/

}
