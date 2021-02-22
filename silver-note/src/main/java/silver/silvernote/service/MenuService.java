package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Menu;
import silver.silvernote.domain.Schedule;
import silver.silvernote.repository.MenuRepository;
import silver.silvernote.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;

    /**
     * 식단 등록
     */
    @Transactional
    public Long save(Menu menu) {
        menuRepository.save(menu);
        return menu.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateMeal(Long id, String meal){
        Menu menu = menuRepository.findById(id).orElseThrow(NoSuchElementException::new);
        menu.updateMeal(meal);
        menuRepository.save(menu);
    }

    /**
     * 물품 삭제
     */
    @Transactional
    public void deleteMenu(Long id){
        menuRepository.deleteById(id);
    }

    /**
     * 전체 식단 조회
     */
    public List<Menu> findMenus() {
        return menuRepository.findAll();
    }


    /**
     * 개별 식단 조회
     */
    public Optional<Menu> findOne(Long menuId) {
        return menuRepository.findById(menuId);
    }


}
