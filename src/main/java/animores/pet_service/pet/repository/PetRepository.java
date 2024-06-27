package animores.pet_service.pet.repository;

import animores.pet_service.pet.entity.Pet;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Long>, PetCustomRepository {
    @Override
    @EntityGraph(attributePaths = {"breed", "image"})
    Optional<Pet> findById(Long id);
    List<Pet> findAllByAccount_id(Long id);
}
