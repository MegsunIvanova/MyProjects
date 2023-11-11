package bg.softuni.autho_moto_manager.repository;

import bg.softuni.autho_moto_manager.model.entity.MakeEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MakeRepository extends JpaRepository<MakeEntity, Long> {

    @EntityGraph(value = "makeWithModels",
            attributePaths = "models")
    @Query("SELECT m FROM MakeEntity m " +
            "ORDER BY m.name ASC ")
    List<MakeEntity> findAllOrdered();

    Optional<MakeEntity> findByName(String name);

}
