package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item {

    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    @NotBlank
    private String name;

    @Positive(message = "가격을 확인하세요")
    private int price;

    // 분류?

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Item(String name, int price) {
        this.name = name;
        this.price = price;
    }
    public void updateData(String name, int price) {
        this.name = name;
        this.price = price;
    }
}
