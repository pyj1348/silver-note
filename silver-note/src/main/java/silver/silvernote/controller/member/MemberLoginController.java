package silver.silvernote.controller.member;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import silver.silvernote.domain.Admin;
import silver.silvernote.domain.center.Center;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.*;
import silver.silvernote.responsemessage.*;
import silver.silvernote.service.AdminService;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.MemberService;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;


@RestController
@RequiredArgsConstructor
public class MemberLoginController {

    private final AdminService adminService;
    private final MemberService memberService;
    private final CenterService centerService;

    /**
     * 로그인
     * */
    @PostMapping("/members/login")
    public ResponseEntity<Message> userLogin(@RequestBody @Valid MemberLoginRequestDto request) {

        Admin admin = adminService.findOneByLoginId(request.getLoginId()).orElse(null);

        if(admin != null){
            if(!admin.getPassword().equals(request.getPassword()))
                throw new NoPasswordElementException();

            AdminLoginResponseDto adminLogin = new AdminLoginResponseDto(admin);
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", adminLogin), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.OK);
        } else {
            Member member = memberService.findOneByLoginId(request.getLoginId()).orElseThrow(NoIdElementException::new);

            if (!member.getPassword().equals(request.getPassword()))
                throw new NoPasswordElementException();

            MemberLoginResponseDto memberLogin = new MemberLoginResponseDto(member);

            if (memberLogin.getType().equals("M"))
                return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                        new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", memberLogin), // STATUS, MESSAGE, DATA
                        HttpHeaderCreator.createHttpHeader(),
                        HttpStatus.OK);

            else
                return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                        new Message(HttpStatusEnum.UNAUTHORIZED, "권한이 없습니다", memberLogin), // STATUS, MESSAGE, DATA
                        HttpHeaderCreator.createHttpHeader(),
                        HttpStatus.UNAUTHORIZED);
        }

    }

    @PostMapping("/members/login/join-possible")
    public ResponseEntity<Message> isPossibleToJoin(@RequestBody @Valid JoinPossibleRequestDto request){

        Member member = memberService.findOne(request.getName(), request.getRrn()).orElseThrow(NoSuchElementException::new);

        if (member.getStatus().equals(JoinStatus.JOINED)){
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.FORBIDDEN, "이미 가입된 회원입니다", LocalDateTime.now()), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.FORBIDDEN);
        }

        else {
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.OK, "가입가능한 회원입니다", new JoinPossibleResponseDto(member)), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/members/login/existing/new")
    public ResponseEntity<Message> joinExistingMember(@RequestBody @Valid ExistingMemberJoinRequestDto request) {

        Long memberId = memberService.updateLoginData(request.getMemberId(), request.getLoginId(), request.getPassword());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(memberId,  LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    @PostMapping("/members/login/manager/new")
    public ResponseEntity<Message> joinManager(@RequestBody @Valid ManagerJoinRequestDto request) {

        Center center = centerService.findOne(request.centerId).orElseThrow(NoSuchElementException::new);

        Manager manager = Manager.BuilderByParam()
                .center(center)
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .sex(request.getSex())
                .rrn(request.getRrn())
                .phone(request.getPhone())
                .address(request.getAddress())
                .zipcode(request.getZipcode())
                .status(JoinStatus.WAITING)
                .build();

        memberService.save(manager);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(manager.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    @PostMapping("/members/login/family/new")
    public ResponseEntity<Message> saveFamilyUser(@RequestBody @Valid FamilyJoinRequestDto request) {

        Patient patient = memberService.findOnePatient(request.getPatientId()).orElseThrow(NoSuchElementException::new);
        Center center = centerService.findOne(request.centerId).orElseThrow(NoSuchElementException::new);

        Family family = Family.BuilderByParam()
                .center(center)
                .patient(patient)
                .name(request.getName())
                .loginId(request.getLoginId())
                .password(request.getPassword())
                .sex(request.getSex())
                .rrn(request.getRrn())
                .phone(request.getPhone())
                .address(request.getAddress())
                .zipcode(request.getZipcode())
                .status(JoinStatus.JOINED)
                .build();

        memberService.save(family);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(family.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }


    /**
     * Request DTO
     * */


    @Data
    static class MemberLoginRequestDto {

        @NotNull(message = "로그인 ID를 확인하세요")
        private String loginId;

        @NotNull(message = "비밀번호를 확인하세요")
        private String password;

    }

    @Data
    static class JoinPossibleRequestDto {

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @NotBlank (message = "주민등록번호를 확인하세요")
        @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$", message = "주민등록번호를 확인하세요")
        private String rrn; // resident registration number

        @NotNull (message = "센터 ID를 확인하세요")
        private Long centerId;

    }

    @Data
    static class ExistingMemberJoinRequestDto {

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

        @NotBlank (message = "아이디를 확인하세요")
        @Column(unique=true)
        private String loginId;


        @NotBlank( message = "비밀번호를 확인하세요")
        private String password;

    }


    @Data
    static class ManagerJoinRequestDto {

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @NotBlank (message = "아이디를 확인하세요")
        private String loginId;

        @NotBlank (message = "비밀번호를 확인하세요")
        private String password;

        @NotBlank (message = "주민등록번호를 확인하세요")
        @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$", message = "주민등록번호를 확인하세요")
        private String rrn; // resident registration number

        @NotBlank (message = "성별을 확인하세요 (남|여)")
        @Pattern(regexp = "^남$|^여$", message = "성별을 확인하세요 (남|여)")
        private String sex;

        @NotBlank (message = "전화번호를 확인하세요")
        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "전화번호를 확인하세요")
        private String phone;

        @NotBlank(message = "주소를 확인하세요")
        private String address;

        @NotBlank(message = "우편번호를 확인하세요")
        private String zipcode;

        @NotBlank(message = "센터 ID를 확인하세요")
        private Long centerId;
    }

    @Data
    static class FamilyJoinRequestDto {

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @NotBlank (message = "로그인 ID를 확인하세요")
        private String loginId;

        @NotBlank (message = "비밀번호를 확인하세요")
        private String password;

        @NotBlank (message = "주민등록번호를 확인하세요")
        @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$", message = "주민등록번호를 확인하세요")
        private String rrn; // resident registration number

        @NotBlank (message = "성별을 확인하세요 (남|여)")
        @Pattern(regexp = "^남$|^여$", message = "성별을 확인하세요 (남|여)")
        private String sex;

        @NotBlank (message = "전화번호를 확인하세요")
        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "전화번호를 확인하세요")
        private String phone;

        @NotBlank (message = "주소를 확인하세요")
        private String address;

        @NotBlank (message = "우편번호를 확인하세요")
        private String zipcode;

        @NotNull(message = "회원 ID를 확인하세요")
        private Long patientId;

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;
    }


    /**
     * Response DTO
     * */

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class AdminLoginResponseDto {
        private String name;
        private String type;

        public AdminLoginResponseDto(Admin admin){
            this.name = admin.getName();
            this.type = admin.getClass().getSimpleName().substring(0,1);
        }
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MemberLoginResponseDto {
        private String name;
        private Long memberId;
        private Long centerId;
        private String type;

        public MemberLoginResponseDto(Member member){
            this.name = member.getName();
            this.centerId = member.getCenter().getId();
            this.memberId = member.getId();
            this.type = member.getClass().getSimpleName().substring(0,1);
        }
    }

    @Data
    static class JoinPossibleResponseDto {

        private Long memberId;
        private Long centerId;
        private String type;

        public JoinPossibleResponseDto(Member member) {
            this.memberId = member.getId();
            this.centerId = member.getCenter().getId();
            this.type = member.getClass().getSimpleName().substring(0, 1);
        }
    }
}
