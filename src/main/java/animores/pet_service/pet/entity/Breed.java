package animores.pet_service.pet.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Builder
public class Breed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Species species;

    private String name;

    protected Breed(Long id, Species species, String name) {
        this.id = id;
        this.species = species;
        this.name = name;
    }
}
