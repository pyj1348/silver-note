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
public class UserJoinController {




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



    /**
     * Request DTO
     * */



    // 로그인
    // 패스워드변경

    /**
     * Response DTO
     * */



}
