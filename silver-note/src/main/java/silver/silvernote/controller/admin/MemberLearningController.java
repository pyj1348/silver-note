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
import java.util.*;
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
    public ResponseEntity<Message> findMemberLearnings(@RequestParam("memberId") Long memberId,
                                                       @RequestParam("start") String start,
                                                       @RequestParam("end") String end) {


        List<MemberLearning> collect = memberLearningService
                .findMemberLearningsByMemberAndDate(memberId, LocalDate.parse(start), LocalDate.parse(end));

        Map<LocalDate, MemberLearningResponseDto> map = new HashMap<>();

        for(MemberLearning memberLearning : collect){
            MemberLearningResponseDto dto;

            if (map.containsKey(memberLearning.getDate())){
                dto = map.get(memberLearning.getDate());
            }
            else{
                dto = new MemberLearningResponseDto();
            }

            dto.addLearning(memberLearning);
            map.put(memberLearning.getDate(), dto);
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", map), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/member-learnings/new")
    public ResponseEntity<Message> saveMemberLearning(@RequestBody @Valid MemberLearningRequestDto request) {
        memberLearningService.deleteMemberLearningsByMemberAndDate(request.getMemberIds(), request.getStart(), request.getEnd());

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
        private LocalDate start;

        @NotNull
        private LocalDate end;

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
        private List<Long> learningIds = new ArrayList<>();
        private List<String> learningNames = new ArrayList<>();

        public void addLearning(MemberLearning memberLearning){
            this.learningIds.add(memberLearning.getLearning().getId());
            this.learningNames.add(memberLearning.getLearning().getName());
        }
    }

}
