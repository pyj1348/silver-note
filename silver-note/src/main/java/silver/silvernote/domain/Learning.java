package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Learning {

    @Id
    @GeneratedValue
    @Column(name = "learning_id")
    private Long id;

    @NotBlank
    private String name;

    private String description;
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "category_id")
    private LearningCategory category;

    // 분류?

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Learning(String name, String description, String url, LearningCategory category) {
        this.name = name;
        this.description = description;
        this.url = url;
        this.category = category;
    }

    public void updateData(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }
}
