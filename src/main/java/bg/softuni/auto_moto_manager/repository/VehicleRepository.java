package bg.softuni.auto_moto_manager.repository;

import bg.softuni.auto_moto_manager.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByVin(String vin);

    Optional<VehicleEntity> findByUuid(String uuid);

    @EntityGraph(value = "vehicleWithCosts",
            attributePaths = {"costCalculation", "model", "primaryImage"})
    Optional<VehicleEntity> getByUuid(String uuid);

    List<VehicleEntity> findAllByOwner_Email(String email);

}
