package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CostRepository extends JpaRepository<CostEntity, Long> {

    List<CostEntity> findAllByVehicle_Uuid(String vehicleUuid);

    List<CostEntity> findAllByVehicle_UuidAndCompleted(String vehicleUuid, boolean completed);

    Optional<CostEntity> findByIdAndVehicle_Uuid(Long id, String vehicleUuid);
}
