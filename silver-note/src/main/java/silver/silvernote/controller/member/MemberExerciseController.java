package silver.silvernote.controller.member;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Exercise;
import silver.silvernote.domain.MemberExercise;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.ExerciseService;
import silver.silvernote.service.MemberExerciseService;
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
public class MemberExerciseController {

    private final MemberExerciseService memberExerciseService;
    private final MemberService memberService;
    private final ExerciseService exerciseService;



    /**
     * 조회
     * */
    @GetMapping("/member-exercises")
    public ResponseEntity<Message> findMemberExercises() {

        List<MemberExerciseResponseDto> collect = memberExerciseService.findMemberExercises().stream().map(MemberExerciseResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/member-exercises/new")
    public ResponseEntity<Message> saveMemberCenterLearning(@RequestBody @Valid MemberExerciseController.MemberExerciseRequestDto request) {
        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);
        Exercise exercise = exerciseService.findOne(request.getExerciseId()).orElseThrow(NoSuchElementException::new);

        MemberExercise memberExercise = MemberExercise.BuilderByParam()
                                        .date(request.getDate())
                                        .member(member)
                                        .exercise(exercise)
                                        .build();

        memberExerciseService.save(memberExercise);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(memberExercise.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/member-exercises/{id}")
    public ResponseEntity<Message> updateProgress(@PathVariable("id") Long id, @RequestParam("progress") int progress) {

        memberExerciseService.updateProgress(id, progress);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }



    /**
     * 삭제
     * */
    @DeleteMapping("/member-exercises/{id}")
    public ResponseEntity<Message> deleteMemberExercise(@PathVariable("id") Long id) {

        memberExerciseService.deleteMemberExercise(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class MemberExerciseRequestDto {
        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

        @NotNull(message = "운동 ID를 확인하세요")
        private Long exerciseId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MemberExerciseResponseDto {
        private Long id;
        private LocalDate date;
        private Long memberId;
        private Long exerciseId;

        public MemberExerciseResponseDto(MemberExercise memberExercise) {
            this.id = memberExercise.getId();
            this.date = memberExercise.getDate();
            this.memberId = memberExercise.getMember().getId();
            this.exerciseId = memberExercise.getExercise().getId();
        }
    }

}
