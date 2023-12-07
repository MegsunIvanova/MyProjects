package bg.softuni.auto_moto_manager.repository;

import bg.softuni.auto_moto_manager.model.entity.ExRateHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExRateHistoryRepository extends JpaRepository<ExRateHistoryEntity, Long> {
}
