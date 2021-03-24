package silver.silvernote.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import silver.silvernote.domain.Item;
import silver.silvernote.domain.Payment;
import silver.silvernote.domain.center.Center;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.ItemService;
import silver.silvernote.service.MemberService;
import silver.silvernote.service.PaymentService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;
    private final MemberService memberService;
    private final CenterService centerService;
    private final ItemService itemService;

    /**
     * 조회
     * */
    @GetMapping("/payments")
    public ResponseEntity<Message> findPayments() {
        List<PaymentResponseDto> collect = paymentService.findPayments().stream().map(PaymentResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/payments/new")
    public ResponseEntity<Message> saveOrder(@RequestBody @Valid PaymentRequestDto request) {

        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);
        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);
        Item item = itemService.findOne(request.getItemId()).orElseThrow(NoSuchElementException::new);

        LocalDate expiredDate = request.getPaymentDate().plusMonths(item.getMonth());

        Payment payment = Payment.BuilderByParam()
                    .paymentDate(request.getPaymentDate())
                    .expiredDate(expiredDate)
                    .item(item)
                    .member(member)
                    .center(center)
                    .build();
        
        paymentService.save(payment);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(payment.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
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
    static class PaymentRequestDto {

        @NotNull (message = "구매 날짜를 확인하세요")
        @PastOrPresent (message = "구매 날짜를 확인하세요")
        private LocalDate paymentDate;

        @NotNull(message = "상품 ID를 확인하세요")
        private Long itemId;

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class PaymentResponseDto {
        private Long id;
        private Long price;
        private LocalDate paymentDate;
        private LocalDate expiredDate;
        private Long centerId;
        private String centerName;
        private Long memberId;
        private String memberName;

        public PaymentResponseDto(Payment payment) {
            this.id = payment.getId();
            this.price = payment.getItem().getPrice();
            this.paymentDate = payment.getPaymentDate();
            this.expiredDate = payment.getExpiredDate();
            this.centerId = payment.getCenter().getId();
            this.centerName = payment.getCenter().getName();
            this.memberId = payment.getMember().getId();
            this.memberName = payment.getMember().getName();
        }
    }
}
