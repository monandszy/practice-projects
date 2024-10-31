package code.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public DaoAuthenticationProvider authProvider(
       PasswordEncoder passwordEncoder,
       UserDetailsService userDetailsService
   ) {
      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
      provider.setPasswordEncoder(passwordEncoder);
      provider.setUserDetailsService(userDetailsService);
      return provider;
   }

   @Bean
   public AuthenticationManager authManager(
       HttpSecurity http,
       AuthenticationProvider authProvider
   ) throws Exception {
      return http
          .getSharedObject(AuthenticationManagerBuilder.class)
          .authenticationProvider(authProvider)
          .build();
   }

   @Bean
   @ConditionalOnProperty(value = "spring.security.enabled",
       havingValue = "true", matchIfMissing = true)
   public SecurityFilterChain securityEnabled(HttpSecurity http) throws Exception {
      return http
          .csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(requests -> requests
              .requestMatchers("/login", "/error", "/register").permitAll()
              .requestMatchers(
                  "/queue/**",
                  "/images/**"
              ).hasAnyAuthority("ACCOUNT")
              .requestMatchers(
                  "/home/**"
              ).hasAnyAuthority("ADMIN")
              .requestMatchers(
                  "/restaurants/**",
                  "/restaurant/**",
                  "/menu/**",
                  "/discover/**",
                  "/myOrder/**"
              ).hasAnyAuthority("CLIENT")
              .requestMatchers(
                  "/myRestaurants/**",
                  "/myRestaurant/**",
                  "/myMenu/**",
                  "/manage/**",
                  "/discover/**",
                  "/order/**"
              ).hasAnyAuthority("SELLER")
          )
          .exceptionHandling(e -> e
              .accessDeniedPage("/queue")
          )
          .formLogin(form -> form
              .loginPage("/login")
              .defaultSuccessUrl("/queue", true)
              .permitAll()
          )
          .logout(logout -> logout.logoutSuccessUrl("/login")
              .invalidateHttpSession(true)
              .deleteCookies("JSESSIONID")
              .permitAll()
          ).build();
   }

   @Bean
   @ConditionalOnProperty(value = "spring.security.enabled", havingValue = "false")
   public SecurityFilterChain securityDisabled(HttpSecurity http) throws Exception {
      return http
          .csrf(AbstractHttpConfigurer::disable)
          .authorizeHttpRequests(requests -> requests
              .anyRequest()
              .permitAll()
          )
          .build();
   }

}