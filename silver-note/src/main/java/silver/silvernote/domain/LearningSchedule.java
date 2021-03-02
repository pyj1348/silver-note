package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningSchedule {

    @Id
    @GeneratedValue
    @Column(name = "learning_schedule_id")
    private Long id;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "center_id")
    private Center center;


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public LearningSchedule(LocalDate date, Center center) {
        this.date = date;
        this.center = center;

    }

}
