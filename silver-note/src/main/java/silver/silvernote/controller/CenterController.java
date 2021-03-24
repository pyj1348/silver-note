package silver.silvernote.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.center.Center;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CenterController {

    private final CenterService centerService;

    /**
     * 조회
     * */
    @GetMapping("/centers/all")
    public ResponseEntity<Message> findCenters() {

        List<CenterResponseDto> collect = centerService.findCenters().stream().map(CenterResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    @GetMapping("/centers")
    public ResponseEntity<Message> findCenter(@RequestParam("id") Long centerId) {

        Center center = centerService.findOne(centerId).orElseThrow(NoSuchElementException::new);
        CenterResponseDto centerDto = new CenterResponseDto(center);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", centerDto), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/centers/new")
    public ResponseEntity<Message> saveCenter(@RequestBody @Valid CenterRequestDto request) {

        Center center = Center.BuilderByParam()
                    .name(request.getName())
                    .phone(request.getPhone())
                    .description(request.getDescription())
                    .address(request.getAddress())
                    .zipcode(request.getZipcode())
                    .build();

        centerService.save(center);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(center.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/centers/{id}")
    public ResponseEntity<Message> updateCenter(@PathVariable("id") Long id,
                                                @RequestBody @Valid CenterRequestDto request) {

        centerService.updateData(id, request.getName(), request.getPhone(), request.getDescription(), request.getAddress(), request.getZipcode());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/centers/{id}")
    public ResponseEntity<Message> deleteCenter(@PathVariable("id") Long id) {

        centerService.deleteCenter(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class CenterRequestDto {

        @NotBlank (message = "이름을 확인하세요")
        private String name;

        @NotBlank(message = "전화번호를 확인하세요")
        @Pattern(regexp = "^0(?:2|\\d{2})-(?:\\d{3}|\\d{4})-\\d{4}$", message = "전화번호를 확인하세요")
        private String phone;

        @NotBlank(message = "주소를 확인하세요")
        private String address;

        @NotBlank(message = "우편번호를 확인하세요")
        private String zipcode;

        private String description;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class CenterResponseDto {
        private Long id;
        private String name;
        private String phone;
        private String address;
        private String zipcode;
        private String description;

        public CenterResponseDto(Center center){
            this.id = center.getId();
            this.name = center.getName();
            this.phone = center.getPhone();
            this.description = center.getDescription();
            this.address = center.getAddress();
            this.zipcode = center.getZipcode();
        }
    }
}
