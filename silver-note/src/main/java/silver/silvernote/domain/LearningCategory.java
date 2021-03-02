package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningCategory {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public LearningCategory(String name) {
        this.name = name;
    }

    public void updateName(String name){ this.name = name; }
}