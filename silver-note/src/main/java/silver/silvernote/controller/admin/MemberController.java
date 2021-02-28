package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.*;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final CenterService centerService;

    /**
     * 조회
     * */
    @GetMapping("/members")
    public ResponseEntity<Message> findMembers() {

        Map<String, List<MembersResponseDto>> members = new HashMap<>();

        members.put("manager", memberService.findManagers().stream().map(MembersResponseDto::new).collect(Collectors.toList()));
        members.put("employee", memberService.findEmployees().stream().map(MembersResponseDto::new).collect(Collectors.toList()));
        members.put("patient", memberService.findPatients().stream().map(MembersResponseDto::new).collect(Collectors.toList()));
        members.put("family", memberService.findFamilies().stream().map(MembersResponseDto::new).collect(Collectors.toList()));

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", members), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }
    @GetMapping("/patients")
    public ResponseEntity<Message> findPatients(@RequestParam("centerId") Long centerId) {

        List<PatientsResponseDto> patients = memberService.findPatientsByCenterId(centerId).stream().map(PatientsResponseDto::new).collect(Collectors.toList());;

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", patients), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }



    /**
     * 생성
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

        if (request.getType().equals("M")) {
            member = Manager.BuilderByParam()
                    .center(center)
                    .name(request.getName())
                    .sex(request.getSex())
                    .rrn(request.getRrn())
                    .phone(request.getPhone())
                    .address(address)
                    .build();

        }
        else if (request.getType().equals("E")){
            member = Employee.BuilderByParam()
                    .center(center)
                    .name(request.getName())
                    .sex(request.getSex())
                    .rrn(request.getRrn())
                    .phone(request.getPhone())
                    .address(address)
                    .build();

        }
        else{
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
    @GetMapping("/members/manager/waiting")
    public ResponseEntity<Message> findWaitingMembers(){
        List<MembersResponseDto> collect = memberService.findWaitingManagers().stream().map(MembersResponseDto::new)
                .collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 조회되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 승인 대기 관리자 승인/거절 요청
     * */
    @PutMapping("/members/manager/{id}/approval") // 수정
    public ResponseEntity<Message> responseToJoinRequest(@PathVariable("id") Long id,
                                                         @RequestParam("type") String type) {
        if (type.equals("approve"))
            memberService.updateManagerPermission(id, PermissionStatus.APPROVED);
        else if (type.equals("decline"))
            memberService.updateManagerPermission(id, PermissionStatus.DECLINED);
        else{
            return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                    new Message(HttpStatusEnum.BAD_REQUEST, "메시지 형식이 올바르지 않습니다", "type을 확인하세요 (approve|decline)"), // STATUS, MESSAGE, DATA
                    HttpHeaderCreator.createHttpHeader(),
                    HttpStatus.BAD_REQUEST);
        }

        Member member = memberService.findOne(id).orElseThrow(NoSuchElementException::new);;

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(member.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }
    /**
     * 수정
     * */
    @PutMapping("/members/{id}")
    public ResponseEntity<Message> updateData(@PathVariable("id") Long id, @RequestBody MemberUpdateRequestDto request) {
        Address address = Address.BuilderByParam()
                .city(request.getCity())
                .street(request.getStreet())
                .zipcode(request.getZipcode())
                .build();

        memberService.updateData(id, request.getPhone(), address);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
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

        @Pattern(regexp = "^M$|^E$|^P$", message = "타입을 확인하세요 (M|E|P)")
        private String type;
    }

    @Data
    static class MemberUpdateRequestDto {

        @Pattern(regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$", message = "휴대폰번호를 확인하세요 (010-0000-0000)")
        private String phone;

        private String city;
        private String street;
        private String zipcode;
    }



    @Data
    static class TypeFilter {
        private boolean manager;
        private boolean employee;
        private boolean family;
        private boolean patient;
    }

    /**
     * Response DTO
     * */

    @Data
    static class MemberApprovalRequestDto {
        @Pattern(regexp = "^a$|^d$", message = "상태값을 확인하세요 (a | d)")
        private String status;

        @NotNull
        private TypeFilter type;
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MembersResponseDto {
        private Long id;
        private String name;
        private String phone;
        private String sex;
        private Address address;
        private JoinStatus joinStatus;
        private Long centerId;

        public MembersResponseDto(Member member){
            this.id = member.getId();
            this.name = member.getName();
            this.sex = member.getSex();
            this.phone = member.getPhone();
            this.address = member.getAddress();
            this.joinStatus = member.getStatus();
            this.centerId = member.getCenter().getId();
        }
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class PatientsResponseDto {
        private Long id;
        private String name;

        public PatientsResponseDto(Patient patient){
            this.id = patient.getId();
            this.name = patient.getName();
        }
    }
}
