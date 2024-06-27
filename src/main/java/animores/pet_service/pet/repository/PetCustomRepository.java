package animores.pet_service.pet.repository;

import animores.pet_service.pet.dao.PetDao;

import java.util.List;

public interface PetCustomRepository {
    List<PetDao> findAllByAccount_IdWithImages(Long id);
}
