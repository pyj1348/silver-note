package silver.silvernote.controller.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.User;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.*;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.service.FamilyService;
import silver.silvernote.service.MemberService;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.UserService;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserJoinController {

    private final UserService userService;
    private final CenterService centerService;
    private final MemberService memberService;
    private final FamilyService familyService;


    /**
     * 조회
     * */

    /**
     * 생성
     * */

    /**
     * 삭제
     * */

    /**
     * 수정
     * */

    @GetMapping("/users") // 필요한가
    public ResponseEntity<Message> findUsers() {

        List<UserResponseDto> collect = userService.findUsers().stream().map(UserResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    @PostMapping("/users/members/verification")
    public ResponseEntity<Message> verifyMemberToJoin(@RequestBody @Valid VerifyingRequestDto request){

        // 조회된 결과가 없으면 Advice가 not found 반환
        Member member = memberService.findOne(request.getName(), request.getRrn()).orElseThrow(NoSuchElementException::new);

        if (member.getStatus().equals(JoinStatus.JOINED)){
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.FORBIDDEN, "이미 가입된 회원입니다", new SimpleResponseDto(null,  LocalDateTime.now())), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.FORBIDDEN);
        }

        else {
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.OK, "가입가능한 회원입니다", new SimpleResponseDto(member.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.OK);
        }
    }

    @PostMapping("/users/members/member/new")
    public ResponseEntity<Message> connectToMember(@RequestBody @Valid UserJoinRequestDto request) {

        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);

        User user = User.BuilderByParam()
                .emailId(request.getEmailId())
                .password(request.getPassword())
                .center(member.getCenter())
                .member(member)
                .build();

        userService.save(user);
        memberService.updateStatus(user.getMember().getId(), JoinStatus.JOINED); // 승인

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(user.getId(),  LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }


    @PostMapping("/users/members/family/new")
    public ResponseEntity<Message> saveFamilyUser(@RequestBody @Valid FamilyJoinRequestDto request) {

        Patient patient = memberService.findOnePatient(request.getPatientId()).orElseThrow(NoSuchElementException::new);
        Center center = centerService.findOne(request.centerId).orElseThrow(NoSuchElementException::new);
        Address address = Address.BuilderByParam()
                .city(request.getCity())
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .build();

        Family family = Family.BuilderByParam()
                .center(center)
                .patient(patient)
                .name(request.getName())
                .sex(request.getSex())
                .rrn(request.getRrn())
                .phone(request.getPhone())
                .address(address)
                .status(JoinStatus.JOINED)
                .build();

        User user = User.BuilderByParam()
                .emailId(request.getEmailId())
                .password(request.getPassword())
                .center(family.getCenter())
                .member(family)
                .build();

        memberService.save(family);
        userService.save(user);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(user.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * Request DTO
     * */

    @Data
    static class VerifyingRequestDto {

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$")
        private String rrn; // resident registration number

    }

    @Data
    static class UserJoinRequestDto {

        @NotNull
        private Long memberId;

        @NotBlank (message = "아이디를 확인하세요 (silver@naver.com)")
        @Email
        @Column(unique=true)
        private String emailId;

        @NotBlank (message = "비밀번호를 확인하세요 (대문자, 소문자, 숫자, 특수문자 포함 8자 이상)")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
                message = "비밀번호를 확인하세요 (대문자, 소문자, 숫자, 특수문자 포함 8자 이상)")
        private String password;

    }

    @Data
    static class FamilyJoinRequestDto {

        @NotNull
        private Long patientId;

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @NotBlank (message = "아이디를 확인하세요 (silver@naver.com)")
        @Email
        private String emailId;

        @NotBlank (message = "비밀번호를 확인하세요 (대문자, 소문자, 숫자, 특수문자 포함 8자 이상)")
        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
                message = "비밀번호를 확인하세요 (대문자, 소문자, 숫자, 특수문자 포함 8자 이상)")
        private String password;

        @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$")
        private String rrn; // resident registration number

        @NotBlank (message = "성별을 입력하세요 (남|여)")
        @Pattern(regexp = "^남$|^여$")
        private String sex;

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰번호를 확인하세요 (010-0000-0000)")
        private String phone;

        private String city;
        private String street;
        private String zipcode;

        @NotNull
        private Long centerId;
    }

    /**
     * Response DTO
     * */


    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class UserResponseDto {
        private Long id;
        private String emailId;
        private Long centerId;
        private Long memberID;

        public UserResponseDto(User user){
            this.id = user.getId();
            this.emailId = user.getEmailId();
            this.centerId = user.getCenter().getId();
            this. memberID = user.getMember().getId();
        }
    }
}
