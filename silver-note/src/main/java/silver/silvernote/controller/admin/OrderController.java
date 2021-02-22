package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Album;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Item;
import silver.silvernote.domain.Order;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.ItemService;
import silver.silvernote.service.MemberService;
import silver.silvernote.service.OrderService;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    /**
     * 조회
     * */
    @GetMapping("/orders")
    public ResponseEntity<Message> findOrders() {
        List<OrderResponseDto> collect = orderService.findOrders().stream().map(OrderResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/orders/new")
    public ResponseEntity<Message> saveOrder(@RequestBody @Valid OrderRequestDto request) {

        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);
        Item item = itemService.findOne(request.getItemId()).orElseThrow(NoSuchElementException::new);

        Order order = Order.BuilderByParam()
                    .date(request.getDate())
                    .quantity(request.getQuantity())
                    .member(member)
                    .item(item)
                    .build();
        
        orderService.save(order);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(order.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */

    /**
     * 삭제
     * */

    /**
     * Request DTO
     * */

    @Data
    static class OrderRequestDto {

        @NotNull
        @PastOrPresent
        private LocalDate date;

        @Size(min = 1)
        private int quantity;

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

        @NotNull(message = "아이템 ID를 확인하세요")
        private Long itemId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class OrderResponseDto {
        private Long id;
        private int quantity;
        private LocalDate date;
        private Long memberId;
        private Long itemId;


        public OrderResponseDto(Order order) {
            this.id = order.getId();
            this.date = order.getDate();
            this.quantity = order.getQuantity();
            this.memberId = order.getMember().getId();
            this.itemId = order.getItem().getId();
        }
    }
}
