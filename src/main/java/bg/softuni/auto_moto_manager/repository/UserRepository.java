package bg.softuni.auto_moto_manager.repository;

import bg.softuni.auto_moto_manager.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);
//    Optional<UserEntity> findByVehicle_Uuid(String email, String uuid);
}
