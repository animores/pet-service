package animores.pet_service.pet.dto.response;


import animores.pet_service.pet.entity.Species;

public record SpeciesResponse(
        Long id,
        String name
) {
    public static SpeciesResponse fromEntity(Species species) {
        return new SpeciesResponse(species.getId(), species.getName());
    }
}
