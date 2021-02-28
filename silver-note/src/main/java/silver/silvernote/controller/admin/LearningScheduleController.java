package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.LearningSchedule;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.DailyLearningService;
import silver.silvernote.service.LearningScheduleService;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.LearningService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
public class LearningScheduleController {

    private final LearningScheduleService scheduleService;
    private final DailyLearningService dailyLearningService;
    private final CenterService centerService;
    private final LearningService learningService;

    /**
     * 조회
     * */
    @GetMapping("/learning-schedules")
    public ResponseEntity<Message> findLearningSchedules(@RequestParam("centerId") Long centerId,
                                                       @RequestParam("start") String start,
                                                       @RequestParam("end") String end) {


        Center center = centerService.findOne(centerId).orElseThrow(NoSuchElementException::new);


        /**
         * data: [
         * {    "date" : "2021-02-01".
         *      "learnings : [
         *                      id, id, id
         *                   ]
         *      ]
         * },
         * {...}
         * ]
         * */
        //수정할것

        // ids, dates
        List<LearningScheduleResponseDto> data = new ArrayList<>();

        List<LearningSchedule> schedules = scheduleService.findSchedulesByCenterAndDate(center,
                                                                                        LocalDate.parse(start),
                                                                                        LocalDate.parse(end));

        for(LearningSchedule schedule : schedules){
            List<DailyLearning> dailyLearnings = dailyLearningService.findLearningsBySchedule(schedule);

            List<Long> learningIds = new ArrayList<>();
            for (DailyLearning learning : dailyLearnings){
                learningIds.add(learning.getLearning().getId());
            }

            data.add(new LearningScheduleResponseDto(schedule.getDate(), learningIds));
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", data), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/learning-schedules/new")
    public ResponseEntity<Message> saveLearningSchedule(@RequestBody @Valid LearningScheduleRequestDto request) {
        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        LearningSchedule schedule = LearningSchedule.BuilderByParam()
                                    .center(center)
                                    .date(request.getDate())
                                    .build();

        scheduleService.save(schedule);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(schedule.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */


    /**
     * 삭제
     * */
    @DeleteMapping("/learning-schedules/{id}")
    public ResponseEntity<Message> deleteLearningSchedule(@PathVariable("id") Long id) {

        scheduleService.deleteLearningSchedule(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class LearningScheduleRequestDto {

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;

        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;
    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class LearningScheduleResponseDto {
        private LocalDate date;
        private List<Long> learningIds;

        public LearningScheduleResponseDto(LocalDate date, List<Long> learningIds) {
            this.date = date;
            this.learningIds = learningIds;
        }
    }

}
