package bg.softuni.auto_moto_manager.service.impl;

import bg.softuni.auto_moto_manager.model.dto.binding.UserEditDTO;
import bg.softuni.auto_moto_manager.model.entity.RoleEntity;
import bg.softuni.auto_moto_manager.model.entity.UserEntity;
import bg.softuni.auto_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.auto_moto_manager.repository.RoleRepository;
import bg.softuni.auto_moto_manager.repository.UserRepository;
import bg.softuni.auto_moto_manager.service.UserService;
import bg.softuni.auto_moto_manager.util.EntityForTests;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class UserServiceImplTestIT {
    @Autowired
    private UserService userServiceToTest;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    UserEntity testUser;
    RoleEntity userRole;
    RoleEntity adminRole;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
        createEntities();
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void testGetUserForEdit() {
        testUser = userRepository.save(EntityForTests
                .createTestUser(Set.of(userRole, adminRole)));

        Optional<UserEditDTO> actualUserOpt = userServiceToTest.getUserForEdit(testUser.getEmail());

        Assertions.assertTrue(actualUserOpt.isPresent());
        Assertions.assertEquals(testUser.getEmail(), actualUserOpt.get().getEmail());
        Assertions.assertEquals(testUser.getName(), actualUserOpt.get().getName());
        Assertions.assertEquals(testUser.getRoles().stream().anyMatch(isAdmin()),
                actualUserOpt.get().isAdmin());
    }

    @Test
    void testEditRolesShouldRemoveAdminRole() {
        testUser = userRepository.save(EntityForTests
                .createTestUser(Set.of(userRole, adminRole)));

        userServiceToTest.editRoles(new UserEditDTO()
                .setName(testUser.getName())
                .setEmail(testUser.getEmail())
                .setAdmin(false));

        Optional<UserEntity> editedUser1 = userRepository.findByEmail(testUser.getEmail());
        Assertions.assertTrue(editedUser1.isPresent());
        Assertions.assertEquals(testUser.getEmail(), editedUser1.get().getEmail());
        Assertions.assertEquals(testUser.getName(), editedUser1.get().getName());
        assertFalse(editedUser1.get().getRoles().stream().anyMatch(isAdmin()));
    }

    @Test
    void testEditRolesShouldAddAdminRole() {
        testUser = userRepository.save(EntityForTests
                .createTestUser(Set.of(userRole)));

        userServiceToTest.editRoles(new UserEditDTO()
                .setName(testUser.getName())
                .setEmail(testUser.getEmail())
                .setAdmin(true));

        Optional<UserEntity> editedUser2 = userRepository.findByEmail(testUser.getEmail());
        Assertions.assertTrue(editedUser2.isPresent());
        Assertions.assertEquals(testUser.getEmail(), editedUser2.get().getEmail());
        Assertions.assertEquals(testUser.getName(), editedUser2.get().getName());
        assertTrue(editedUser2.get().getRoles().stream().anyMatch(isAdmin()));
    }

    private static Predicate<RoleEntity> isAdmin() {
        return r -> r.getRole().equals(UserRoleEnum.ADMIN);
    }

    private void createEntities() {
        userRole = roleRepository.save(new RoleEntity(UserRoleEnum.USER));
        adminRole = roleRepository.save(new RoleEntity(UserRoleEnum.ADMIN));
    }

}