package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberCenterLearning {

    @Id
    @GeneratedValue
    @Column(name = "member_exercise_id")
    private Long id;

    @NotNull
    private LocalDate date;

    @Size(min = 0)
    private int progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "center_learning_id")
    private LearningSchedule learningSchedule;


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public MemberCenterLearning(LocalDate date, int progress, Member member, LearningSchedule learningSchedule) {
        this.date = date;
        this.progress = progress;
        this.member = member;
        this.learningSchedule = learningSchedule;
    }

    public void updateProgress(int progress){ this.progress = progress; }
}
