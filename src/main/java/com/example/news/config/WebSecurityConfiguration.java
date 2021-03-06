package com.example.news.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers("/news/**", "/").authenticated()
                .anyRequest().permitAll()
                .and()
                .headers()
                .frameOptions().disable()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/news")
                .and()
                .logout()
                .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .csrf().disable();

    }
}
