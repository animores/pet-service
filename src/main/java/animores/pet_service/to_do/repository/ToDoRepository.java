package animores.pet_service.to_do.repository;

import animores.pet_service.to_do.entity.ToDo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
    List<ToDo> findAllByDate(LocalDate date);

    List<ToDo> findAllByAccountId(Long accountId);

    Optional<ToDo> findByIdAndAccount_Id(Long id, Long accountId);
}
