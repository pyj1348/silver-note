package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CenterLearning {

    @Id
    @GeneratedValue
    @Column(name = "center_study_id")
    private Long id;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_id")
    private Learning learning;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public CenterLearning(LocalDate date,Center center, Learning learning) {
        this.date = date;
        this.center = center;
        this.learning = learning;
    }
}
