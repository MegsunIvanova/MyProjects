package bg.softuni.auto_moto_manager.repository;

import bg.softuni.auto_moto_manager.model.entity.ExRateHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ExRateHistoryRepository extends JpaRepository<ExRateHistoryEntity, Long> {

   List<ExRateHistoryEntity> findAllByUpdateOnBefore(LocalDateTime dateTime);
}
