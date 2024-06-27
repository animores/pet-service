package animores.pet_service.pet.service.impl;

import animores.pet_service.account.entity.Account;
import animores.pet_service.common.exception.CustomException;
import animores.pet_service.common.exception.ExceptionCode;
import animores.pet_service.pet.dao.PetDao;
import animores.pet_service.pet.dto.PetDto;
import animores.pet_service.pet.dto.request.PetCreateRequest;
import animores.pet_service.pet.dto.request.PetUpdateRequest;
import animores.pet_service.pet.dto.response.*;
import animores.pet_service.pet.entity.Breed;
import animores.pet_service.pet.entity.Pet;
import animores.pet_service.pet.entity.PetImage;
import animores.pet_service.pet.entity.Species;
import animores.pet_service.pet.repository.BreedRepository;
import animores.pet_service.pet.repository.PetImageRepository;
import animores.pet_service.pet.repository.PetRepository;
import animores.pet_service.pet.repository.SpeciesRepository;
import animores.pet_service.pet.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Service
public class PetServiceImpl implements PetService {
    private final SpeciesRepository speciesRepository;
    private final BreedRepository breedRepository;
    private final PetRepository petRepository;
    private final PetImageRepository petImageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Pet> checkAccountPets(Long accountId, List<Long> petIds) {
        List<Pet> pets = petRepository.findAllByAccount_id(accountId);

        if(petIds == null || petIds.isEmpty()){
            return pets;
        } else {
            Set<Long> petSet = pets.stream().map(Pet::getId)
                    .collect(HashSet::new, Set::add, Set::addAll);

            petIds.forEach(petId -> {
                if(!petSet.contains(petId)){
                    throw new CustomException(ExceptionCode.ILLEGAL_PET_IDS);
                }
            });

            return pets.stream().filter(pet -> petSet.contains(pet.getId())).toList();
        }
    }

    @Override
    public List<SpeciesResponse> getSpecies() {
        List<Species> species = speciesRepository.findAll();
        return species.stream().map(SpeciesResponse::fromEntity).toList();
    }

    @Override
    public List<BreedResponse> getBreedsOfSpecies(Long speciesId) {
        List<Breed> breeds = breedRepository.findAllBySpecies_Id(speciesId);
        return breeds.stream().map(BreedResponse::fromEntity).toList();
    }

    @Override
    public List<PetImageResponse> getPetImages(Long speciesId) {

        return petImageRepository.findAllBySpeciesId(speciesId).stream()
                .map(PetImageResponse::fromEntity).toList();
    }

    @Transactional
    @Override
    public PetCreateResponse createPet(Account account, PetCreateRequest request) {
        Breed breed = breedRepository.findById(request.breedId())
                                     .orElseThrow(() -> new IllegalArgumentException("Breed not found"));

        Long imageId = request.imageId() == null ? breed.getSpecies().getBasicPetImage().getId(): request.imageId();
        PetImage image = petImageRepository.getReferenceById(imageId);

        Pet pet = Pet.createFromRequest(account, request, breed, image);
        pet = petRepository.save(pet);

        return new PetCreateResponse(pet.getId(),pet.getName());
    }

    @Transactional(readOnly = true)
    @Override
    public List<PetDto> getPets(Account account) {
        List<PetDao> pets = petRepository.findAllByAccount_IdWithImages(account.getId());
        return pets.stream().map(PetDto::fromDao).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public GetPetDetailResponse getPet(Long petId) {

        return GetPetDetailResponse.fromEntity(petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ExceptionCode.ILLEGAL_PET_IDS)));
    }

    @Transactional
    @Override
    public PetCreateResponse updatePet(Long petId, PetUpdateRequest request) {
        Pet pet = petRepository.findById(petId)
                .orElseThrow(() -> new CustomException(ExceptionCode.ILLEGAL_PET_IDS));

        Breed breed = request.breedId() == null ? pet.getBreed() : breedRepository.findById(request.breedId())
                .orElseThrow(() -> new IllegalArgumentException("Breed not found"));

        PetImage image = request.imageId() == null ? pet.getImage() : petImageRepository.findById(request.imageId())
                .orElseThrow(() -> new IllegalArgumentException("Image not found"));

        pet.update(request, breed, image);
        return new PetCreateResponse(pet.getId(),pet.getName());
    }

    @Transactional
    @Override
    public void deletePet(Long petId) {
        petRepository.deleteById(petId);
    }
}
