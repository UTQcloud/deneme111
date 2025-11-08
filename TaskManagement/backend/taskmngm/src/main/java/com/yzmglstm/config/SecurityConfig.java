package com.yzmglstm.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.Customizer;
import org.springframework.security.core.userdetails.UserDetailsService; // YENİ
import org.springframework.security.core.userdetails.UsernameNotFoundException; // YENİ
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.yzmglstm.repository.UsersRepository; // YENİ
import com.yzmglstm.entities.Users; // YENİ

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Autowired
    private UsersRepository usersRepository; // YENİ
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    // ========== YENİ EKLENEN METOT ==========
    @Bean
    public UserDetailsService userDetailsService() {
        return email -> {
            // Veritabanından kullanıcıyı bul
            Users user = usersRepository.findByMail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Kullanıcı bulunamadı: " + email));
            
            // Spring Security'nin anlayacağı formata çevir
            return org.springframework.security.core.userdetails.User
                .withUsername(user.getMail())
                .password(user.getPassword()) // Zaten şifreli olarak kayıtlı
                .authorities("USER") // Rol ver
                .build();
        };
    }
    // =========================================
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .httpBasic(Customizer.withDefaults()) 
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/api/auth/save").permitAll() 
                .requestMatchers("/api/auth/login").permitAll()
                .requestMatchers("/api/tasks").authenticated() 
                .requestMatchers("/api/tasks/**").authenticated()
                .anyRequest().authenticated() 
            );
        
        return http.build();
    }
}