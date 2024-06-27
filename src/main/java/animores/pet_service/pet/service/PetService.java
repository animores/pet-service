package animores.pet_service.pet.service;


import animores.pet_service.account.entity.Account;
import animores.pet_service.pet.dto.PetDto;
import animores.pet_service.pet.dto.request.PetCreateRequest;
import animores.pet_service.pet.dto.request.PetUpdateRequest;
import animores.pet_service.pet.dto.response.*;
import animores.pet_service.pet.entity.Pet;

import java.util.List;

public interface PetService {
    List<Pet> checkAccountPets(Long accountId, List<Long> petIds);
    List<SpeciesResponse> getSpecies();
    List<BreedResponse> getBreedsOfSpecies(Long speciesId);
    List<PetImageResponse> getPetImages(Long speciesId);
    PetCreateResponse createPet(Account account, PetCreateRequest request);
    List<PetDto> getPets(Account account);

    GetPetDetailResponse getPet(Long petId);

    PetCreateResponse updatePet(Long petId, PetUpdateRequest request);

    void deletePet(Long petId);
}
