package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.*;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CenterService centerService;

    /**
     * 관리자 조회
     * */
    @GetMapping("/members/managers")
    public ResponseEntity<Message> findManagers(@RequestParam("centerId") Long centerId) {

        List<MemberResponseDto> managers = memberService.findManagersByCenterId(centerId).stream().map(MemberResponseDto::new).collect(Collectors.toList());;

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", managers), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 근로자 조회
     * */
    @GetMapping("/members/employees")
    public ResponseEntity<Message> findEmployees(@RequestParam("centerId") Long centerId) {
        List<MemberResponseDto> employees = memberService.findEmployeesByCenterId(centerId).stream().map(MemberResponseDto::new).collect(Collectors.toList());;

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", employees), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 회원 조회
     * */
    @GetMapping("/members/patients")
    public ResponseEntity<Message> findPatients(@RequestParam("centerId") Long centerId) {

        List<PatientResponseDto> patients = memberService.findPatientsByCenterId(centerId).stream().map(PatientResponseDto::new).collect(Collectors.toList());;

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", patients), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 가족 조회
     * */
    @GetMapping("/members/family")
    public ResponseEntity<Message> findFamily(@RequestParam("centerId") Long centerId) {

        List<FamilyResponseDto> family = memberService.findFamilyByCenterId(centerId).stream().map(FamilyResponseDto::new).collect(Collectors.toList());;

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", family), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 근로자 / 회원 생성
     * */
    @PostMapping("/members/new")
    public ResponseEntity<Message> saveMember(@RequestBody @Valid MemberSaveRequestDto request) {
        Address address = Address.BuilderByParam()
                .city(request.getCity())
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .build();

        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        Member member;

        if (request.getType().equals("E")){
            member = Employee.BuilderByParam()
                    .center(center)
                    .name(request.getName())
                    .sex(request.getSex())
                    .rrn(request.getRrn())
                    .phone(request.getPhone())
                    .address(address)
                    .build();

        }
        else { // else if "P"
            member = Patient.BuilderByParam()
                    .center(center)
                    .name(request.getName())
                    .sex(request.getSex())
                    .rrn(request.getRrn())
                    .phone(request.getPhone())
                    .address(address)
                    .build();
        }

        memberService.save(member);


        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(member.getId(),  LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 승인 대기 관리자 조회
     * */
    @GetMapping("/members/managers/waiting")
    public ResponseEntity<Message> findWaitingMembers(){
        List<MemberResponseDto> collect = memberService.findWaitingManagers().stream().map(MemberResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 승인 대기 관리자 승인/거절 요청
     * */
    @PutMapping("/members/managers/join-request/{id}") // 수정
    public ResponseEntity<Message> responseToJoinRequest(@PathVariable("id") Long id,
                                                         @RequestParam("type") String type) {
        if (type.equals("approve"))
            memberService.updateStatus(id, JoinStatus.JOINED);
        else if (type.equals("decline"))
            memberService.updateStatus(id, JoinStatus.YET);
        else{
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.BAD_REQUEST, "메시지 형식이 올바르지 않습니다", "type을 확인하세요 (approve|decline)"), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findOne(id).orElseThrow(NoSuchElementException::new);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(member.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 관리자, 매니저, 가족 정보수정
     * */
    @PutMapping("/members/{id}")
    public ResponseEntity<Message> updateData(@PathVariable("id") Long id, @RequestBody @Valid MemberUpdateRequestDto request) {
        Address address = Address.BuilderByParam()
                .city(request.getCity())
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .build();

        memberService.updateData(id, request.getEmail(), request.getPhone(), address);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     * */
    @PutMapping("/members/patients/{id}")
    public ResponseEntity<Message> updateData(@PathVariable("id") Long patientId, @RequestBody @Valid PatientUpdateRequestDto request) {
        Address address = Address.BuilderByParam()
                .city(request.getCity())
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .build();

        memberService.updateData(patientId, request.getEmail(), request.getPhone(), address);
        memberService.updateGrade(patientId, request.getGrade());
        memberService.changeManager(patientId, request.getManagerId());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(patientId, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/members/{id}")
    public ResponseEntity<Message> deleteMember(@PathVariable("id") Long id) {

        memberService.deleteMember(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */

    @Data
    static class MemberSaveRequestDto {

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @Email
        private String email;

        @NotBlank (message = "성별을 입력하세요 (남|여)")
        @Pattern(regexp = "^남$|^여$")
        private String sex;

        @Pattern(regexp = "^\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])-[1-4][0-9]{6}$")
        private String rrn; // resident registration number

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰번호를 확인하세요 (010-0000-0000)")
        private String phone;

        private String city;
        private String street;
        private String zipcode;

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;

        @Pattern(regexp = "^E$|^P$", message = "타입을 확인하세요 (M|E|P)")
        private String type;
    }

    @Data
    static class MemberUpdateRequestDto {

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰번호를 확인하세요 (010-0000-0000)")
        private String phone;

        private String city;
        private String street;
        private String zipcode;

        @Email(message = "이메일을 확인하세요")
        private String email;
    }

    @Data
    static class PatientUpdateRequestDto {

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰번호를 확인하세요 (010-0000-0000)")
        private String phone;

        private String city;
        private String street;
        private String zipcode;

        @Email(message = "이메일을 확인하세요")
        private String email;

        @NotNull(message = "담당자 ID를 확인하세요")
        private Long managerId;

        @Positive(message = "등급을 확인하세요")
        private int grade;
    }

    /**
     * Response DTO
     * */

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MemberResponseDto {
        private Long id;
        private String type;
        private String loginId;
        private String password;
        private String name;
        private String email;
        private String sex;
        private String rrn; // resident registration number
        private String phone;
        private String city;
        private String street;
        private String zipcode;
        private JoinStatus status;

        public MemberResponseDto(Member member) {
            this.id = member.getId();
            this.loginId = member.getLoginId();
            this.password = member.getPassword();
            this.name = member.getName();
            this.email = member.getEmail();
            this.sex = member.getSex();
            this.rrn = member.getRrn();
            this.phone = member.getPhone();
            this.city = member.getAddress().getCity();
            this.street = member.getAddress().getStreet();
            this.zipcode = member.getAddress().getZipcode();
            this.status = member.getStatus();
            this.type = member.getClass().getSimpleName().substring(0,1);
        }
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class PatientResponseDto {
        private Long id;
        private String type;
        private String loginId;
        private String password;
        private String name;
        private String email;
        private String sex;
        private String rrn; // resident registration number
        private String phone;
        private String city;
        private String street;
        private String zipcode;
        private Long managerId;
        private String managerName;
        private int grade;
        private JoinStatus status;

        public PatientResponseDto(Patient patient){
            this.id = patient.getId();
            this.name = patient.getName();
            this.loginId = patient.getLoginId();
            this.password = patient.getPassword();
            this.email = patient.getEmail();
            this.sex = patient.getSex();
            this.rrn = patient.getRrn(); // resident registration number
            this.phone = patient.getPhone();
            this.city = patient.getAddress().getCity();
            this.street = patient.getAddress().getStreet();
            this.zipcode = patient.getAddress().getZipcode();
            this.managerId = patient.getManager().getId();
            this.managerName = patient.getManager().getName();
            this.grade = patient.getGrade();
            this.status = patient.getStatus();
            this.type = patient.getClass().getSimpleName().substring(0,1);
        }
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class FamilyResponseDto {
        private Long id;
        private String type;
        private String loginId;
        private String password;
        private String name;
        private String email;
        private String sex;
        private String rrn; // resident registration number
        private String phone;
        private String city;
        private String street;
        private String zipcode;
        private Long patientId;
        private String patientName;
        private JoinStatus status;

        public FamilyResponseDto(Family family){
            this.id = family.getId();
            this.loginId = family.getLoginId();
            this.password = family.getPassword();
            this.name = family.getName();
            this.email = family.getEmail();
            this.sex = family.getSex();
            this.rrn = family.getRrn();
            this.phone = family.getPhone();
            this.city = family.getAddress().getCity();
            this.street = family.getAddress().getStreet();
            this.zipcode = family.getAddress().getZipcode();
            this.patientId = family.getPatient().getId();
            this.patientName = family.getPatient().getName();
            this.status = family.getStatus();
            this.type = family.getClass().getSimpleName().substring(0,1);
        }
    }

}
