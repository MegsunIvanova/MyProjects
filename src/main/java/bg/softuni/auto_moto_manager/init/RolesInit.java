package bg.softuni.auto_moto_manager.init;

import bg.softuni.auto_moto_manager.model.entity.RoleEntity;
import bg.softuni.auto_moto_manager.model.enums.UserRoleEnum;
import bg.softuni.auto_moto_manager.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class RolesInit implements CommandLineRunner {
    private final RoleRepository roleRepository;

    public RolesInit(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        if (roleRepository.count() == 0) {
            roleRepository
                    .saveAll(Arrays.stream(UserRoleEnum.values())
                            .map(RoleEntity::new)
                            .toList());
        }
    }
}
