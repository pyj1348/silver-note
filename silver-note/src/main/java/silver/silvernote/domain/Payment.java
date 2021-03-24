package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.center.Center;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Payment {

    @Id
    @GeneratedValue
    @Column (name = "payment_id")
    private Long id;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private LocalDate expiredDate;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Payment(Long id, LocalDate paymentDate, LocalDate expiredDate, Item item, Member member, Center center) {
        this.id = id;
        this.paymentDate = paymentDate;
        this.expiredDate = expiredDate;
        this.item = item;
        this.member = member;
        this.center = center;
    }

}
