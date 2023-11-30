package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<VehicleEntity, Long> {

    Optional<VehicleEntity> findByVin(String vin);

    Optional<VehicleEntity> findByUuid(String uuid);

//    @EntityGraph(value = "vehicleWithCosts",
//            attributePaths = "costCalculation")
//    @Query("SELECT v FROM VehicleEntity v WHERE v.uuid = :uuid")
//    Optional<VehicleEntity> findVehicleWithCostsByUuid(String uuid);

    long countByUuidAndOwner_EmailAndSaleIsNull (String uuid, String ownerEmail);
    long countByUuidAndSaleIsNull (String uuid);

    boolean existsByUuid(String uuid);
    boolean existsByUuidAndSaleIsNull (String uuid);
    boolean existsByUuidAndOwner_EmailAndSaleIsNull (String uuid, String ownerEmail);

}
