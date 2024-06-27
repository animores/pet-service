package animores.pet_service.pet.dto.response;

import animores.pet_service.pet.entity.Breed;

public record BreedResponse(Long id, String name) {

    public static BreedResponse fromEntity(Breed breed) {
        return new BreedResponse(breed.getId(), breed.getName());
    }
}
