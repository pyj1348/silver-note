package silver.silvernote.domain.member;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import silver.silvernote.domain.center.Center;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED) // 한 테이블에 상속관계 데이터를 다 넣는다
@DiscriminatorColumn(name = "dtype") // dtype으로 구분
public abstract class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @NotBlank (message = "이름을 확인하세요")
    private String name;


    @Column(unique=true)
    private String loginId;

    //    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
//            message = "비밀번호를 확인하세요 (영어, 숫자, 특수문자 포함 8자 이상)")
    private String password;


    @Email
    @Column(unique=true)
    private String email;

    @Pattern(regexp = "^남$|^여$")
    private String sex;

    @Column(unique=true)
    @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$")
    private String rrn; // resident registration number

    @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰번호를 확인하세요 (010-0000-0000)")
    private String phone;

    private String address;

    private String zipcode;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "center_id")
    private Center center;

    @Enumerated(EnumType.STRING)
    private JoinStatus status;

//    private bool isCenterAdmin;


    @PrePersist // default 세팅
    public void prePersist() {
        this.status = this.status == null ? JoinStatus.YET : this.status;

        if (this.sex == null){
            if(this.rrn.charAt(7) == '1' | this.rrn.charAt(7) == '3'){
                this.sex = "남";
            }
            else if(this.rrn.charAt(7) == '2' | this.rrn.charAt(7) == '4'){
                this.sex = "여";
            }
        }
    }

    protected void createMember(Center center, String loginId, String password, String name, String email, String sex, String rrn, String phone, String address, String zipcode, JoinStatus status) {
        this.center = center;
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.sex = sex;
        this.rrn = rrn;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
        this.status = status;
    }


    public void updateStatus(JoinStatus status) { this.status = status; }

    public void updateData(String email, String phone, String address, String zipcode) {
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.zipcode = zipcode;
    }

    public void updateLoginData(String loginId, String password) {
        this.loginId = loginId;
        this.password = password;
    }

}
