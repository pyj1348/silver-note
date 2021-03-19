package silver.silvernote.controller.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.User;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Family;
import silver.silvernote.domain.member.JoinStatus;
import silver.silvernote.domain.member.Member;
import silver.silvernote.domain.member.Patient;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.FamilyService;
import silver.silvernote.service.MemberService;
import silver.silvernote.service.UserService;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class UserController {

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

    @PostMapping("/users/login")
    public ResponseEntity<Message> userLogin(@RequestBody @Valid UserLoginRequestDto request) {

        User user = userService.findOneByEmailIdAndPassword(request.getEmailId(), request.getPassword()).orElseThrow(NoSuchElementException::new);

        UserLoginResponseDto userLogin = new UserLoginResponseDto(user);


        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", userLogin), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * Request DTO
     * */

    @Data
    static class UserLoginRequestDto {

        @NotBlank (message = "아이디를 확인하세요 (silver@naver.com)")
        @Email
        private String emailId;

        @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$",
                message = "비밀번호를 확인하세요 (영어, 숫자, 특수문자 포함 8자 이상)")
        private String password;

    }

    /**
     * Response DTO
     * */

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class UserLoginResponseDto {
        private Long memberId;
        private Long centerId;

        public UserLoginResponseDto(User user){
            this.centerId = user.getCenter().getId();
            this. memberId = user.getMember().getId();
        }
    }
}
