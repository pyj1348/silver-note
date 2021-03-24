package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import silver.silvernote.domain.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p ORDER BY p.center.name, p.paymentDate desc")
    List<Payment> findAllOrderBy();
    List<Payment> findAllByCenterIdOrderByPaymentDateDesc(Long centerId);

}
