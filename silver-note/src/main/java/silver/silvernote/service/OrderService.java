package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Item;
import silver.silvernote.domain.Order;
import silver.silvernote.domain.member.Member;
import silver.silvernote.repository.ItemRepository;
import silver.silvernote.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    /**
     * 구매 등록
     */
    @Transactional
    public Long save(Order order) {
        orderRepository.save(order);
        return order.getId();
    }

    /**
     * 정보 변경
     */


    /**
     * 물품 삭제
     */

    /**
     * 전체 아이템 조회
     */
    public List<Order> findOrders() {
        return orderRepository.findAll();
    }
    public List<Order> findOrdersByMember(Member member) { return orderRepository.findOrdersByMember(member); }

    /**
     * 개별 아이템 조회
     */
    public Optional<Order> findOne(Long itemId) { return orderRepository.findById(itemId); }


}
