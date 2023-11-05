package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class ApplicationUserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public ApplicationUserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(this::mapToUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("User " + email + " was not found!"));
    }

    private UserDetails mapToUserDetails (UserEntity userEntity) {
        return User
                .withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles()
                        .stream()
                        .map(ApplicationUserDetailsServiceImpl::mapToGrantedAuthority)
                        .toList())
                .build();
    }

    private static GrantedAuthority mapToGrantedAuthority(RoleEntity roleEntity) {
        return new SimpleGrantedAuthority("ROLE_" + roleEntity.getRole().name());
    }
}
