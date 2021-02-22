package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.CenterLearning;
import silver.silvernote.domain.MemberCenterLearning;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberCenterLearningController {

    private final MemberCenterLearningService memberCenterLearningService;
    private final MemberService memberService;
    private final CenterLearningService centerLearningService;

    /**
     * 조회
     * */
    @GetMapping("/member-center-learnings")
    public ResponseEntity<Message> findMemberCenterLearnings() {

        List<MemberCenterLearningResponseDto> collect = memberCenterLearningService.findMemberCenterLearnings().stream().map(MemberCenterLearningResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/member-center-learnings/new")
    public ResponseEntity<Message> saveMemberCenterLearning(@RequestBody @Valid MemberCenterLearningRequestDto request) {
        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);
        CenterLearning centerLearning = centerLearningService.findOne(request.getCenterLearningId()).orElseThrow(NoSuchElementException::new);

        MemberCenterLearning memberCenterLearning = MemberCenterLearning.BuilderByParam()
                                                    .date(request.getDate())
                                                    .member(member)
                                                    .centerLearning(centerLearning)
                                                    .build();

        memberCenterLearningService.save(memberCenterLearning);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(memberCenterLearning.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/member-center-learnings/{id}")
    public ResponseEntity<Message> updateProgress(@PathVariable("id") Long id, @RequestParam("progress") int progress) {

        memberCenterLearningService.updateProgress(id, progress);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }



    /**
     * 삭제
     * */
    @DeleteMapping("/member-center-learnings/{id}")
    public ResponseEntity<Message> deleteMemberCenterLearning(@PathVariable("id") Long id) {

        memberCenterLearningService.deleteMemberCenterLearning(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class MemberCenterLearningRequestDto {
        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;

        @Size(min = 0)
        private int progress;

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

        @NotNull(message = "센터학습 ID를 확인하세요")
        private Long centerLearningId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MemberCenterLearningResponseDto {
        private Long id;
        private LocalDate date;
        private Long memberId;
        private Long centerLearningId;

        public MemberCenterLearningResponseDto(MemberCenterLearning memberCenterLearning) {
            this.id = memberCenterLearning.getId();
            this.date = memberCenterLearning.getDate();
            this.memberId = memberCenterLearning.getMember().getId();
            this.centerLearningId = memberCenterLearning.getCenterLearning().getId();
        }
    }

}
