package bg.softuni.autho_moto_manager.service.impl;

import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.entity.UserEntity;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.autho_moto_manager.repository.UserRepository;
import bg.softuni.autho_moto_manager.util.EntityForTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApplicationUserDetailsServiceTest {
    private ApplicationUserDetailsService serviceToTest;

    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        serviceToTest = new ApplicationUserDetailsService(mockUserRepository);
    }

    @Test
    void testUserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class,
                () -> serviceToTest.loadUserByUsername("pesho@softuni.bg")
        );
    }

    @Test
    void testUserFoundException() {
        UserEntity testUserEntity = createTestUser();

        when(mockUserRepository.findByEmail(testUserEntity.getEmail()))
                .thenReturn(Optional.of(testUserEntity));

        UserDetails userDetails = serviceToTest.loadUserByUsername(testUserEntity.getEmail());

        Assertions.assertNotNull(userDetails);
        Assertions.assertEquals(testUserEntity.getEmail(), userDetails.getUsername(),
                "Username is not mapped to email.");
        Assertions.assertEquals(testUserEntity.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(2, userDetails.getAuthorities().size());
        Assertions.assertTrue(
                containsAuthority(userDetails, "ROLE_" + UserRoleEnum.ADMIN.name()),
                "The user is not admin");
        Assertions.assertTrue(
                containsAuthority(userDetails, "ROLE_" + UserRoleEnum.USER.name()),
                "The user is not admin");
    }

    private static UserEntity createTestUser() {
        return EntityForTests.createTestUser(Set.of(
                createRole(UserRoleEnum.ADMIN),
                createRole(UserRoleEnum.USER)));
    }

    private static RoleEntity createRole(UserRoleEnum role) {
        RoleEntity roleEntity = new RoleEntity().setRole(role);
        long id = role.ordinal() + 1;
        roleEntity.setId(id);

        return roleEntity;
    }

    private boolean containsAuthority(UserDetails userDetails, String expectedAuthority) {
        return userDetails.getAuthorities()
                .stream()
                .anyMatch(a -> a.getAuthority().equals(expectedAuthority));
    }

}