package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByVin(String vin);
    Optional<VehicleEntity> findByUuid (String uuid);


}
