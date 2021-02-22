package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Schedule;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.ScheduleService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final CenterService centerService;

    /**
     * 조회
     * */
    @GetMapping("/schedules")
    public ResponseEntity<Message> findSchedules() {
        List<ScheduleResponseDto> collect = scheduleService.findSchedules().stream().map(ScheduleResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/schedules/new")
    public ResponseEntity<Message> saveSchedule(@RequestBody @Valid ScheduleController.ScheduleRequestDto request) {

        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        Schedule schedule = Schedule.BuilderByParam()
                    .date(request.getDate())
                    .context(request.getContext())
                    .center(center)
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
    @PutMapping("/schedules/{id}")
    public ResponseEntity<Message> updateContext(@PathVariable("id") Long id,
                                              @RequestBody String context) { // 향후 파라미터가 많아지면 DTO로 수정 해야함

        scheduleService.updateContext(id, context);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Message> deleteSchedule(@PathVariable("id") Long id) {

        scheduleService.deleteSchedule(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * Request DTO
     * */
    @Data
    static class ScheduleRequestDto {

        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;

        @NotBlank(message = "내용를 확인하세요")
        private String context;

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class ScheduleResponseDto {
        private Long id;
        private LocalDate date;
        private String context;

        public ScheduleResponseDto(Schedule schedule) {
            this.id = schedule.getId();
            this.date = schedule.getDate();
            this.context = schedule.getContext();
        }
    }
}
