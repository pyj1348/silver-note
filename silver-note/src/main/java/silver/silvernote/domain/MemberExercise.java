package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberExercise {

    @Id
    @GeneratedValue
    @Column(name = "member_exercise_id")
    private Long id;

    @NotNull
    private LocalDate date;

    @Size(min = 0)
    private int progress;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "exercise_id")
    private Exercise exercise;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public MemberExercise(LocalDate date, int progress, Member member, Exercise exercise) {
        this.date = date;
        this.progress = progress;
        this.member = member;
        this.exercise = exercise;
    }

    public void updateProgress(int progress){ this.progress = progress; }
}
