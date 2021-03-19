package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import silver.silvernote.domain.member.Member;
import silver.silvernote.domain.member.PermissionStatus;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberLearning {

    @Id
    @GeneratedValue
    @Column(name = "member_learning_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "learning_id")
    private Learning learning;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public MemberLearning(Member member, Learning learning, LocalDate date) {
        this.member = member;
        this.center = member.getCenter();
        this.learning = learning;
        this.date = date;
    }

}
