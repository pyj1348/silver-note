package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningSchedule;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.DailyLearningService;
import silver.silvernote.service.LearningScheduleService;
import silver.silvernote.service.LearningService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
     */
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
        List<LearningScheduleDto> data = new ArrayList<>();

        List<LearningSchedule> schedules = scheduleService.findSchedulesByCenterAndDate(center,
                LocalDate.parse(start),
                LocalDate.parse(end));

        for (LearningSchedule schedule : schedules) {
            List<DailyLearning> dailyLearnings = dailyLearningService.findLearningsBySchedule(schedule);

            List<Long> learningIds = new ArrayList<>();
            for (DailyLearning learning : dailyLearnings) {
                learningIds.add(learning.getLearning().getId());
            }

            data.add(new LearningScheduleDto(schedule.getDate(), learningIds));
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", data), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     */
    @PostMapping("/learning-schedules/new")
    public ResponseEntity<Message> saveLearningSchedule(@RequestBody @Valid LearningScheduleRequestDto request) {
        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        for(DailyLearningsDto dto : request.getData()){
            if (scheduleService.findOneByDateAndCenter(dto.getDate(), center).isPresent()) {
                return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                        new Message(HttpStatusEnum.BAD_REQUEST, "같은 데이터가 있어 생성할 수 없습니다", LocalDateTime.now()), // STATUS, MESSAGE, DATA
                        HttpHeaderCreator.createHttpHeader(),
                        HttpStatus.BAD_REQUEST);
            }


            LearningSchedule schedule = LearningSchedule.BuilderByParam()
                    .center(center)
                    .date(dto.getDate())
                    .build();

            scheduleService.save(schedule);

            for(Long learningId : dto.getLearningIds()){
                Learning learning = learningService.findOne(learningId).orElseThrow(NoSuchElementException::new);

                DailyLearning dailyLearning = DailyLearning.BuilderByParam()
                        .learning(learning)
                        .schedule(schedule)
                        .build();

                dailyLearningService.save(dailyLearning);
            }
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", LocalDateTime.now()), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 수정
     * */
    @PutMapping("/learning-schedules/present")
    public ResponseEntity<Message> updateSchedule(@RequestBody @Valid LearningScheduleRequestDto request) {
        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        for(DailyLearningsDto dto : request.getData()){
            LearningSchedule schedule = scheduleService.findOneByDateAndCenter(dto.getDate(), center).orElseThrow(NoSuchElementException::new);

            List<DailyLearning> presentLearnings = dailyLearningService.findLearningsBySchedule(schedule);

            if (presentLearnings.size() != dto.getLearningIds().size()){
                return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                        new Message(HttpStatusEnum.BAD_REQUEST, "수정할 학습의 개수가 다릅니다", LocalDateTime.now()), // STATUS, MESSAGE, DATA
                        HttpHeaderCreator.createHttpHeader(),
                        HttpStatus.BAD_REQUEST);
            }

            for(int i = 0; i < presentLearnings.size(); i++){
                Learning learning = learningService.findOne(dto.getLearningIds().get(i)).orElseThrow(NoSuchElementException::new);
                dailyLearningService.updateLearning(presentLearnings.get(i).getId(), learning);
            }
        }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", LocalDateTime.now()), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }



    /**
     * 삭제
     */
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
     */
    @Data
    static class LearningScheduleRequestDto {

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;

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
     */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class LearningScheduleDto {
        private LocalDate date;
        private List<Long> learningIds;

        public LearningScheduleDto(LocalDate date, List<Long> learningIds) {
            this.date = date;
            this.learningIds = learningIds;
        }
    }

}
