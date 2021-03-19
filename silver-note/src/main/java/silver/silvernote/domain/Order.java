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
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "orders") // 예약어를 피하기위한 이름 변경
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {

    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @NotNull
    @PastOrPresent
    private LocalDate date;

    @Size(min = 1)
    private int quantity;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Order(LocalDate date, int quantity, Member member, Item item) {
        this.date = date;
        this.quantity = quantity;
        this.member = member;
        this.item = item;
    }

}
