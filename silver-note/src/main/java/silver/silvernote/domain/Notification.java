package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notification {

    @Id
    @GeneratedValue
    @Column(name = "notification_id")
    private Long id;

    @NotBlank
    private String title;
    @NotBlank
    private String context;
    @NotNull
    private LocalDate date;

    @ManyToOne(fetch = LAZY) // Member 테이블을 참조하는 FK
    @JoinColumn(name = "center_id")
    private Center center;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Notification(String title, String context, LocalDate date, Center center) {
        this.title = title;
        this.context = context;
        this.date = date;
        this.center = center;
    }

    public void updateData(String title, String context ) {
        this.title = title;
        this.context = context;
    }
}
