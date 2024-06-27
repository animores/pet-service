package animores.pet_service.pet.repository.impl;

import animores.pet_service.pet.dao.PetDao;
import animores.pet_service.pet.repository.PetCustomRepository;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static animores.pet_service.pet.entity.QPet.pet;
import static animores.pet_service.pet.entity.QPetImage.petImage;

@RequiredArgsConstructor
public class PetCustomRepositoryImpl implements PetCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<PetDao> findAllByAccount_IdWithImages(Long id) {
        return jpaQueryFactory.select(Projections.constructor(
                        PetDao.class,
                        pet.id,
                        pet.name,
                        petImage.url
                ))
                .from(pet)
                .join(pet.image, petImage)
                .where(pet.account.id.eq(id))
                .fetch();
    }
}
