package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import silver.silvernote.domain.member.Member;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank (message = "아이디를 확인하세요 (silver@naver.com)")
    @Email
    @Column(unique=true)
    private String emailId;

    @NotBlank (message = "비밀번호를 확인하세요 (대문자, 소문자, 숫자, 특수문자 포함 8자 이상)")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
            message = "비밀번호를 확인하세요 (대문자, 소문자, 숫자, 특수문자 포함 8자 이상)")
    private String password;


    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "center_id")
    private Center center;

    private String roll;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public User(String emailId, String password, Member member, Center center) {
        this.emailId = emailId;
        this.password = password;
        this.member = member;
        this.center = center;
        this.roll = member.getClass().getSimpleName();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}