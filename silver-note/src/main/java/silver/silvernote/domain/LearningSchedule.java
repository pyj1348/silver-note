package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public LearningSchedule(LocalDate date, Center center) {
        this.date = date;
        this.center = center;

    }

}
