package bg.softuni.autho_moto_manager.configuration;

import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import bg.softuni.autho_moto_manager.service.impl.ApplicationUserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {
    private final UserRepository userRepository;
    private final String rememberMeKey;

    public SecurityConfiguration(UserRepository userRepository,
                                 @Value("${auto.moto.manager.remember.me.key}")
                                 String rememberMeKey) {
        this.userRepository = userRepository;
        this.rememberMeKey = rememberMeKey;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(authorizeHttpRequests -> {
                    authorizeHttpRequests
                            .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                            .permitAll()
                            .requestMatchers("/", "/users/login", "/users/register", "users/login-error")
                            .permitAll()
                            .requestMatchers("/error/**").permitAll()
                            .requestMatchers(HttpMethod.GET, "vehicles/all").permitAll()
                            .requestMatchers(HttpMethod.GET, "vehicle/details/**").permitAll()
                            .requestMatchers("/models/add").hasRole(UserRoleEnum.ADMIN.name())
                            .requestMatchers("/users/edit").hasRole(UserRoleEnum.ADMIN.name())
                            .anyRequest().authenticated();
                })
                .formLogin(formLogin -> {
                    formLogin
                            .loginPage("/users/login")
                            .usernameParameter("email")
                            .passwordParameter("password")
                            .defaultSuccessUrl("/")
                            .failureForwardUrl("/users/login-error");
                })
                .logout(logout -> {
                    logout
                            .logoutUrl("/users/logout")
                            .logoutSuccessUrl("/")
                            .invalidateHttpSession(true);
                })
                .rememberMe(rememberMe -> {
                    rememberMe
                            .key(rememberMeKey)
                            .rememberMeParameter("rememberme")
                            .rememberMeCookieName("rememberme");
                })
                .build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new ApplicationUserDetailsServiceImpl(userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return Pbkdf2PasswordEncoder.defaultsForSpringSecurity_v5_8();
    }
}
