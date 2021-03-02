package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLearning {

    @Id
    @GeneratedValue
    @Column(name = "member_learning_id")
    private Long id;

    private int progress;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
