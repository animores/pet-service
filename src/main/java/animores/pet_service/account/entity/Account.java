package animores.pet_service.account.entity;


import animores.pet_service.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Account extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickname;
    private Boolean isAdPermission;

    public Account(Long id, String email, String nickname, Boolean isAdPermission) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.isAdPermission = isAdPermission;
    }

    public Account() {
    }
}
