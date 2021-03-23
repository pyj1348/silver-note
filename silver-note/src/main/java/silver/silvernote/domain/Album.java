package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor (access = AccessLevel.PROTECTED)
public class Album {

    @Id
    @GeneratedValue
    @Column (name = "album_id")
    private Long id;

    @NotBlank
    private String title;

    @NotNull
    private LocalDate date;
    private String context;

    // image

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member writer;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Album(String title, LocalDate date, String context, Member writer, Center center) {
        this.title = title;
        this.date = date;
        this.context = context;
        this.writer = writer;
        this.center = center;
    }

    public void updateData(String title, String context){
        this.title = title;
        this.context = context;
    }
}
