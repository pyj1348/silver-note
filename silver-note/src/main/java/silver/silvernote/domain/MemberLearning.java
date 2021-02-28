package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLearning {

    @Id
    @GeneratedValue
    @Column(name = "member_learning_id")
    private Long id;

    private int progress;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "learning_schedule_id")
    private DailyLearning dailyLearning;


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public MemberLearning(int progress, Member member, DailyLearning dailyLearning) {
        this.progress = progress;
        this.member = member;
        this.dailyLearning = dailyLearning;
    }

    public void updateProgress(int progress){ this.progress = progress; }
}
