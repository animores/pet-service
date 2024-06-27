package animores.pet_service.pet.controller;

import animores.pet_service.account.entity.Account;
import animores.pet_service.account.service.AccountService;
import animores.pet_service.common.Response;
import animores.pet_service.common.aop.UserInfo;
import animores.pet_service.pet.dto.PetDto;
import animores.pet_service.pet.dto.request.PetCreateRequest;
import animores.pet_service.pet.dto.request.PetUpdateRequest;
import animores.pet_service.pet.dto.response.*;
import animores.pet_service.pet.service.PetService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/pets")
public class PetController {

    private final AccountService accountService;
    private final PetService petService;

    @GetMapping("/species")
    @Operation(summary = "종 조회", description = "종을 조회합니다.")
    public Response<List<SpeciesResponse>> getSpecies() {
        return Response.success(petService.getSpecies());
    }

    @GetMapping("/breeds")
    @Operation(summary = "품종 조회", description = "해당 종의 품종을 조회합니다.")
    public Response<List<BreedResponse>> getBreedsOfSpecies(@RequestParam Long speciesId) {
        return Response.success(petService.getBreedsOfSpecies(speciesId));
    }

    @GetMapping("/images")
    @Operation(summary = "펫 이미지 조회", description = "펫 이미지를 조회합니다.")
    public Response<List<PetImageResponse>> getPetImages(@RequestParam Long speciesId) {
        return Response.success(petService.getPetImages(speciesId));
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @SecurityRequirement(name = "Authorization")
    @UserInfo
    @PostMapping("")
    @Operation(summary = "펫 생성", description = "펫을 생성합니다.")
    public Response<PetCreateResponse> createPet(@RequestBody PetCreateRequest request) {
        Account account = accountService.getAccountFromContext();
        return Response.success(petService.createPet(account, request));
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @SecurityRequirement(name = "Authorization")
    @UserInfo
    @GetMapping("")
    @Operation(summary = "펫 List", description = "해당 계정의 펫을 가져옵니다.")
    public Response<List<PetDto>> getPets() {
        Account account = accountService.getAccountFromContext();
        return Response.success(petService.getPets(account));
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @SecurityRequirement(name = "Authorization")
    @UserInfo
    @GetMapping("/{petId}")
    @Operation(summary = "펫 조회", description = "id 로 해당 펫을 조회합니다.")
    public Response<GetPetDetailResponse> getPet(@PathVariable Long petId) {
        Account account = accountService.getAccountFromContext();
        petService.checkAccountPets(account.getId(), List.of(petId));
        return Response.success(petService.getPet(petId));
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @SecurityRequirement(name = "Authorization")
    @UserInfo
    @PutMapping("/{petId}")
    @Operation(summary = "펫 수정", description = "id 로 해당 펫을 수정합니다.")
    public Response<PetCreateResponse> updatePet(@PathVariable Long petId, @RequestBody PetUpdateRequest request) {
        Account account = accountService.getAccountFromContext();
        petService.checkAccountPets(account.getId(), List.of(petId));
        return Response.success(petService.updatePet(petId, request));
    }

//    @PreAuthorize("hasAuthority('USER')")
//    @SecurityRequirement(name = "Authorization")
    @UserInfo
    @DeleteMapping("/{petId}")
    @Operation(summary = "펫 삭제", description = "id 로 해당 펫을 삭제합니다.")
    public Response<Void> deletePet(@PathVariable Long petId) {
        Account account = accountService.getAccountFromContext();
        petService.checkAccountPets(account.getId(), List.of(petId));
        petService.deletePet(petId);
        return Response.success(null);
    }
}
