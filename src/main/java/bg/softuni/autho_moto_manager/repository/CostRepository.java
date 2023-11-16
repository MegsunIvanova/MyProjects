package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.CostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CostRepository extends JpaRepository<CostEntity, Long> {

}
