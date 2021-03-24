package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.center.Center;
import silver.silvernote.domain.center.PaymentStatus;
import silver.silvernote.repository.CenterRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class CenterService {
    private final CenterRepository centerRepository;
    /**
     * 센터 등록
     */
    @Transactional
    public Long save(Center center) {
        centerRepository.save(center);
        return center.getId();
    }

    /**
     * 정보 수정
     */
    @Transactional
    public Long updateData(Long id, String name, String phone, String description, String address, String zipcode){
        Center center = centerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        center.updateData(name, phone, description, address, zipcode);
        centerRepository.save(center);

        return center.getId();
    }

    @Transactional
    public Long updateStatus(Long id, PaymentStatus status){
        Center center = centerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        center.updateStatus(status);
        centerRepository.save(center);

        return  center.getId();
    }

    /**
     * 센터 삭제
     */

    @Transactional
    public void deleteCenter(Long id){
        centerRepository.deleteById(id);
    }

    /**
     * 전체 센터 조회
     */
    public List<Center> findCenters() {
        return centerRepository.findAll();
    }


    /**
     * 개별 센터 조회
     */
    public Optional<Center> findOne(Long centerId) {
        return centerRepository.findById(centerId);
    }


}
