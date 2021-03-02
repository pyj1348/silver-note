package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningSchedule;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.DailyLearningService;
import silver.silvernote.service.LearningScheduleService;
import silver.silvernote.service.LearningService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class DailyLearningController {

    private final DailyLearningService dailyLearningService;
    private final LearningScheduleService scheduleService;
    private final LearningService learningService;

    /**
     * 조회
     * */

    @GetMapping("/daily-learnings")
    public ResponseEntity<Message> findDailyLearnings() {
        List<DailyLearningResponseDto> collect = dailyLearningService.findLearnings().stream().map(DailyLearningResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * 생성
     * */
    @PostMapping("/daily-learnings/new")
    public ResponseEntity<Message> saveDailyLearning(@RequestBody @Valid DailyLearningRequestDto request) {

        List<Long> createdIds = new ArrayList<>();

        LearningSchedule schedule = scheduleService.findOne(request.getScheduleId()).orElseThrow(NoSuchElementException::new);

        for(Long learningId : request.getLearningIds()) {
            Learning learning = learningService.findOne(learningId).orElseThrow(NoSuchElementException::new);

            DailyLearning dailyLearning = DailyLearning.BuilderByParam()
                    .schedule(schedule)
                    .learning(learning)
                    .build();

            dailyLearningService.save(dailyLearning);

            createdIds.add(dailyLearning.getId());
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new DailyLearningCreateResponseDto(schedule.getId(), createdIds)), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */

    /**
     * 삭제
     * */
    @DeleteMapping("/daily-learnings/{id}")
    public ResponseEntity<Message> deleteDailyLearning(@PathVariable("id") Long id) {

        dailyLearningService.deleteDailyLearning(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class DailyLearningRequestDto {
        @NotNull(message = "학습일정 ID를 확인하세요")
        private Long scheduleId;

        @NotNull(message = "학습 ID를 확인하세요")
        private List<Long> learningIds;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class DailyLearningResponseDto {
        private Long id;
        private Long scheduleId;
        private Long learningId;


        public DailyLearningResponseDto(DailyLearning learning) {
            this.id = learning.getId();
            this.scheduleId = learning.getSchedule().getId();
            this.learningId = learning.getLearning().getId();
        }
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class DailyLearningCreateResponseDto {
        private Long scheduleId;
        private List<Long> dailyLearningIds;

        public DailyLearningCreateResponseDto(Long scheduleId, List<Long> dailyLearningIds) {
            this.scheduleId = scheduleId;
            this.dailyLearningIds = dailyLearningIds;
        }
    }


}
