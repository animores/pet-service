package animores.pet_service.pet.repository;

import animores.pet_service.pet.entity.Breed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    List<Breed> findAllBySpecies_Id(Long speciesId);
}
