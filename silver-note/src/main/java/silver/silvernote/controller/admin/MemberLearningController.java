package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.MemberLearning;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.LearningService;
import silver.silvernote.service.MemberLearningService;
import silver.silvernote.service.MemberService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MemberLearningController {

    private final MemberLearningService memberLearningService;
    private final MemberService memberService;
    private final LearningService learningService;

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
    public ResponseEntity<Message> saveMemberLearning(@RequestBody @Valid MemberLearningRequestDto request) {
        memberLearningService.deleteMemberLearningsByMemberAndDate(request.getMemberIds(), request.getStartDate(), request.getEndDate());

        List<Member> members = memberService.findMembersByIds(request.getMemberIds());

        for(Member member : members) {

            for(DailyLearningsDto dailyLearnings : request.getData()) {
                List<Learning> learnings = learningService.findLearningsByIds(dailyLearnings.getLearningIds());

                for (Learning learning : learnings) {

                    MemberLearning memberLearning = MemberLearning.BuilderByParam()
                            .member(member)
                            .learning(learning)
                            .date(dailyLearnings.getDate())
                            .build();

                    memberLearningService.save(memberLearning);
                }
            }
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", LocalDateTime.now()), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */


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

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
        private LocalDate startDate;

        @NotNull
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd")
        private LocalDate endDate;

        @NotNull
        private List<Long> memberIds;

        @NotNull
        private List<DailyLearningsDto> data;

    }

    @Data
    static class DailyLearningsDto {

        private LocalDate date;
        private List<Long> learningIds;
    }



    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MemberLearningResponseDto {
        private Long id;
        private Long memberId;
        private Long learningId;

        public MemberLearningResponseDto(MemberLearning memberLearning) {
            this.id = memberLearning.getId();
            this.memberId = memberLearning.getMember().getId();
            this.learningId = memberLearning.getLearning().getId();
        }
    }

}
