package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.MakerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MakerRepository extends JpaRepository<MakerEntity, Long> {

    @Query("SELECT m FROM MakerEntity m " +
            "ORDER BY size(m.models) DESC," +
            "m.name ASC ")
    List<MakerEntity> findAllOrdered ();

    Optional<MakerEntity> findByName(String name);
}
