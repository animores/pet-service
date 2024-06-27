package animores.pet_service.to_do.entity;

import animores.pet_service.common.BaseEntity;
import animores.pet_service.profile.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class ToDoInstance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private ToDo toDo;
    private LocalDate date;
    private LocalTime time;
    @OneToOne(fetch = FetchType.LAZY)
    @PrimaryKeyJoinColumn(name = "complete_profile_id")
    private Profile completeProfile;
    private LocalDateTime completeTime;


    public static ToDoInstance fromToDo(ToDo toDo) {
        return ToDoInstance.builder()
                .toDo(toDo)
                .date(toDo.getDate())
                .time(toDo.getTime())
                .build();
    }

    public void setComplete(Profile profile) {
        this.completeProfile = profile;
        this.completeTime = LocalDateTime.now();
    }
}
