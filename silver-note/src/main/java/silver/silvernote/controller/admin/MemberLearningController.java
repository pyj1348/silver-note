package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.MemberLearning;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.DailyLearningService;
import silver.silvernote.service.MemberLearningService;
import silver.silvernote.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberLearningController {

    private final MemberLearningService memberLearningService;
    private final MemberService memberService;
    private final DailyLearningService dailyLearningService;

    /**
     * 조회
     * */
    @GetMapping("/member-learnings")
    public ResponseEntity<Message> findMemberLearnings() {

        List<MemberLearningResponseDto> collect = memberLearningService.findMemberLearnings().stream().map(MemberLearningResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/member-learnings/new")
    public ResponseEntity<Message> saveMemberLearning(@RequestBody @Valid MemberLearningController.MemberLearningRequestDto request) {
        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);
        DailyLearning learning = dailyLearningService.findOne(request.getDailyLearningId()).orElseThrow(NoSuchElementException::new);

        MemberLearning memberLearning = MemberLearning.BuilderByParam()
                                                    .member(member)
                                                    .dailyLearning(learning)
                                                    .build();

        memberLearningService.save(memberLearning);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(memberLearning.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/member-learnings/{id}")
    public ResponseEntity<Message> updateProgress(@PathVariable("id") Long id, @RequestParam("progress") int progress) {

        memberLearningService.updateProgress(id, progress);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/member-learnings/{id}")
    public ResponseEntity<Message> deleteMemberLearning(@PathVariable("id") Long id) {

        memberLearningService.deleteMemberLearning(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class MemberLearningRequestDto {

        private int progress;

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

        @NotNull(message = "센터학습 ID를 확인하세요")
        private Long dailyLearningId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MemberLearningResponseDto {
        private Long id;
        private Long memberId;
        private Long dailyLearningId;

        public MemberLearningResponseDto(MemberLearning memberLearning) {
            this.id = memberLearning.getId();
            this.memberId = memberLearning.getMember().getId();
            this.dailyLearningId = memberLearning.getDailyLearning().getId();
        }
    }

}
