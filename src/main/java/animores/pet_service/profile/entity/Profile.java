package animores.pet_service.profile.entity;

import animores.pet_service.account.entity.Account;
import animores.pet_service.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 일대다 계정
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private String name;

    private String imageUrl;
    private LocalDateTime deletedAt;

    public void setName(String name) {
        this.name = name;
    }
}
