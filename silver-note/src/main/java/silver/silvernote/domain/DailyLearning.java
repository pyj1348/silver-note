package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DailyLearning {

    @Id
    @GeneratedValue
    @Column(name = "daily_learning_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "learning_schedule_id")
    private LearningSchedule schedule;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "learning_id")
    private Learning learning;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public DailyLearning(LearningSchedule schedule, Learning learning) {
        this.schedule = schedule;
        this.learning = learning;
    }

    public void updateLearning(Learning learning){ this.learning = learning; }
}
