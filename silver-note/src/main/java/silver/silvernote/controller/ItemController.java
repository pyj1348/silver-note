package silver.silvernote.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Item;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.ItemService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;


    /**
     * 조회
     * */
    @GetMapping("/items")
    public ResponseEntity<Message> findItems() {
        List<ItemResponseDto> collect = itemService.findItems().stream().map(ItemResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/items/new")
    public ResponseEntity<Message> saveItem(@RequestBody @Valid ItemRequestDto request) {
        Item item = Item.BuilderByParam()
                    .month(request.getMonth())
                    .price(request.getPrice())
                    .build();
        
        itemService.save(item);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(item.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/items/{id}")
    public ResponseEntity<Message> updateItem(@PathVariable("id") Long id, @RequestBody ItemRequestDto request) {

        itemService.updateData(id, request.getMonth(), request.getPrice());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/items/{id}")
    public ResponseEntity<Message> deleteItem(@PathVariable("id") Long id) {

        itemService.deleteItem(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class ItemRequestDto {

        @Positive(message = "기간을 확인하세요")
        private Long month;

        @Positive (message = "가격을 확인하세요")
        private Long price;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class ItemResponseDto {
        private Long id;
        private Long month;
        private Long price;

        public ItemResponseDto(Item item) {
            this.id = item.getId();
            this.month = item.getMonth();
            this.price = item.getPrice();
        }
    }
}
