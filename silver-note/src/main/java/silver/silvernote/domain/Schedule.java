package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "schedule_id")
    private Long id;
    @NotNull
    private LocalDate date;

    @NotBlank
    private String context;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Builder (builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Schedule(LocalDate date, String context, Center center) {
        this.date = date;
        this.context = context;
        this.center = center;
    }

    public void updateContext(String context) {
        this.context = context;
    }
}
