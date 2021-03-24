package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Payment;
import silver.silvernote.domain.member.Member;
import silver.silvernote.repository.PaymentRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {
    private final PaymentRepository paymentRepository;
    /**
     * 구매 등록
     */
    @Transactional
    public Long save(Payment payment) {
        paymentRepository.save(payment);
        return payment.getId();
    }

    /**
     * 정보 변경
     */


    /**
     * 구매정보 삭제
     */

    /**
     * 전체 아이템 조회
     */
    public List<Payment> findPayments() {
        return paymentRepository.findAllOrderBy();
    }
    public List<Payment> findPaymentsByCenterId(Long centerId) {
        return paymentRepository.findAllByCenterIdOrderByPaymentDateDesc(centerId); }

    /**
     * 개별 아이템 조회
     */
    public Optional<Payment> findOne(Long paymentId) { return paymentRepository.findById(paymentId); }


}
