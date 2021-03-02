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
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Menu {
    @Id
    @GeneratedValue
    @Column(name = "menu_id")
    private Long id;
    @NotNull
    private LocalDate date;
    @Embedded
    private Meal meal;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Builder (builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Menu(LocalDate date, Meal meal, Center center) {
        this.date = date;
        this.meal = meal;
        this.center = center;
    }

    public void updateMeal(Meal meal) {
        this.meal = meal;
    }
}
