package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.RoleEntity;
import bg.softuni.autho_moto_manager.model.enums.UserRoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    Optional<RoleEntity> findByRole (UserRoleEnum role);
}
