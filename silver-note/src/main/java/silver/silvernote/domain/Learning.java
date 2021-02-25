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
import javax.validation.constraints.NotEmpty;

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

    // 분류?

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Learning(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }

    public void updateData(String name, String description, String url) {
        this.name = name;
        this.description = description;
        this.url = url;
    }
}
