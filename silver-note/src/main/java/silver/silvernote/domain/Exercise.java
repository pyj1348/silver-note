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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Exercise {

    @Id
    @GeneratedValue
    @Column(name = "exercise_id")
    private Long id;

    @NotBlank
    private String name;

    private String briefDescription;
    private String fullDescription;
    private String url;

    // 분류?

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Exercise(String name, String briefDescription, String fullDescription, String url) {
        this.name = name;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.url = url;
    }
    public void updateData(String name, String briefDescription, String fullDescription, String url) {
        this.name = name;
        this.briefDescription = briefDescription;
        this.fullDescription = fullDescription;
        this.url = url;
    }
}
