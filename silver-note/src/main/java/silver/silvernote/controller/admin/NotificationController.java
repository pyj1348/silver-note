package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Notification;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.NotificationService;

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
public class NotificationController {

    private final NotificationService notificationService;
    private final CenterService centerService;

    /**
     * 조회
     * */
    @GetMapping("/notifications")
    public ResponseEntity<Message> findNotifications() {
        List<NotificationResponseDto> collect = notificationService.findNotifications().stream().map(NotificationResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/notifications/new")
    public ResponseEntity<Message> saveNotification(@RequestBody @Valid NotificationRequestDto request) {

        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        Notification notification = Notification.BuilderByParam()
                    .date(request.getDate())
                    .context(request.getContext())
                    .center(center)
                    .build();
        
        notificationService.save(notification);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(notification.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/notifications/{id}")
    public ResponseEntity<Message> updateData(@PathVariable("id") Long id,
                                              @RequestBody NotificationUpdateRequestDto request) { // 향후 파라미터가 많아지면 DTO로 수정 해야함

        notificationService.updateData(id, request.getTitle(), request.getContext());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/notifications/{id}")
    public ResponseEntity<Message> deleteNotification(@PathVariable("id") Long id) {

        notificationService.deleteNotification(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * Request DTO
     * */

    @Data
    static class NotificationUpdateRequestDto {

        @NotBlank(message = "제목을 확인하세요")
        private String title;

        @NotBlank(message = "내용를 확인하세요")
        private String context;

    }


    @Data
    static class NotificationRequestDto {

                @NotBlank(message = "제목을 확인하세요")
        private String title;

        @NotBlank(message = "내용를 확인하세요")
        private String context;

        @NotNull
        private Long centerId;

        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class NotificationResponseDto {
        private Long id;
        private String title;
        private String context;
        private LocalDate date;

        public NotificationResponseDto(Notification notification) {
            this.id = notification.getId();
            this.title = notification.getTitle();
            this.context = notification.getContext();
            this.date = notification.getDate();
        }
    }
}
