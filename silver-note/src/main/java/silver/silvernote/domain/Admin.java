package silver.silvernote.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Admin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long id;


    @NotBlank (message = "로그인 ID를 확인하세요")
    @Column(unique=true)
    private String loginId;

    @NotBlank (message = "비밀번호를 확인하세요")
    private String password;

    @NotBlank (message = "이름을 확인하세요")
    @Column(unique=true)
    private String name;

    @Builder(builderClassName = "BuilderByParam", builderMethodName = "BuilderByParam")
    public Admin(String name, String loginId, String password) {
        this.name = name;
        this.loginId = loginId;
        this.password = password;
    }

    public void updatePassword(String password){ this.password = password; }
}
