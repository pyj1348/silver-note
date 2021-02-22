package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Item;
import silver.silvernote.repository.CenterRepository;
import silver.silvernote.repository.ItemRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class ItemService {
    private final ItemRepository itemRepository;
    /**
     * 물품 등록
     */
    @Transactional
    public Long save(Item item) {
        itemRepository.save(item);
        return item.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateData(Long id, String name, int price){
        Item item = itemRepository.findById(id).orElseThrow(NoSuchElementException::new);
        item.updateData(name, price);
        itemRepository.save(item);
    }

    /**
     * 물품 삭제
     */
    @Transactional
    public void deleteItem(Long id){
        itemRepository.deleteById(id);
    }

    /**
     * 전체 아이템 조회
     */
    public List<Item> findItems() {
        return itemRepository.findAll();
    }


    /**
     * 개별 아이템 조회
     */
    public Optional<Item> findOne(Long itemId) {
        return itemRepository.findById(itemId);
    }


}
