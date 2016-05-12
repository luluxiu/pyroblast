package com.tigercel;

import com.tigercel.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.StandardPasswordEncoder;

/**
 * Created by freedom on 2016/4/11.
 */

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new StandardPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .eraseCredentials(true)
                .userDetailsService(userService())
                .passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity security) {
        //security.ignoring().antMatchers("/css/**", "/fonts/**", "img/**",  "/js/**");
        security.ignoring().antMatchers("/pyroblast/**");
        security.ignoring().antMatchers("/app/**");
        security.ignoring().antMatchers("/auto/**", "/css/**", "/img/**",  "/js/**", "/pyro/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login").failureUrl("/login?error")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/rule")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout").logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
                .permitAll()
                .and()
            .sessionManagement()
                .sessionFixation().changeSessionId()
                .maximumSessions(4).maxSessionsPreventsLogin(false)
                .and()

        ;
    }
}