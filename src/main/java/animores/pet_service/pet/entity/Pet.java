package animores.pet_service.pet.entity;

import animores.pet_service.account.entity.Account;
import animores.pet_service.common.BaseEntity;
import animores.pet_service.pet.dto.request.PetCreateRequest;
import animores.pet_service.pet.dto.request.PetUpdateRequest;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Pet extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    private Breed breed;

    @ManyToOne(fetch = FetchType.LAZY)
    private PetImage image;

    private String name;

    private LocalDate birthday;

    private int gender;
    private Float weight;

    public static Pet createFromRequest(Account account, PetCreateRequest request, Breed breed, PetImage image) {
        return Pet.builder()
                .account(account)
                .breed(breed)
                .image(image)
                .name(request.name())
                .birthday(request.birthday())
                .weight(request.weight())
                .gender(request.gender() == null ? 0 : request.gender())
                .build();
    }

    public void update(PetUpdateRequest request, Breed breed, PetImage image) {
        this.breed = breed;
        this.image = image;
        if(request.name() != null) { this.name = request.name(); }
        if(request.birthday() != null) { this.birthday = request.birthday(); }
        if(request.gender() != null) { this.gender = request.gender(); }
        if(request.weight() != null) { this.weight = request.weight(); }
    }
}
