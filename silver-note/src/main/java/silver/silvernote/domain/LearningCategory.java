package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LearningCategory {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private LearningCategory parent;

    @PrePersist
    public void prePersist() {
        if(this.parent == null)
            this.parent = this;
    }


    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public LearningCategory(String name, LearningCategory parent) {
        this.name = name;
        this.parent = parent;
    }

    public void updateName(String name){ this.name = name; }
}