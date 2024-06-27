package animores.pet_service.pet.dto.response;


import animores.pet_service.pet.entity.Pet;

import java.time.LocalDate;

public record GetPetDetailResponse(
        Long id,
        String name,
        String imageUrl,
        BreedResponse breed,
        LocalDate birthday,
        int gender,
        Float weight) {

    public static GetPetDetailResponse fromEntity(Pet pet) {
        return new GetPetDetailResponse(
                pet.getId(),
                pet.getName(),
                pet.getImage().getUrl(),
                BreedResponse.fromEntity(pet.getBreed()),
                pet.getBirthday(),
                pet.getGender(),
                pet.getWeight());
    }
}
