package bg.softuni.auto_moto_manager.repository;

import bg.softuni.auto_moto_manager.model.entity.CurrencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<CurrencyEntity, String> {
    @Query("SELECT id FROM CurrencyEntity order by id")
    List<String> findAllCurrencyIds();
}
