package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.Item;
import silver.silvernote.domain.Order;
import silver.silvernote.domain.member.Member;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findOrdersByMember(Member member);

}
