package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.User;
import silver.silvernote.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class UserService {
    private final UserRepository userRepository;

    /**
     * 유저 등록
     */
    @Transactional
    public Long save(User user) {
        userRepository.save(user);
        return user.getId();
    }

    /**
     * 정보 수정
     */


    /**
     * 유저 삭제
     */

    @Transactional
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }

    /**
     * 전체 센터 조회
     */
    public List<User> findUsers() {
        return userRepository.findAll();
    }


    /**
     * 아이디로 개별 센터 조회
     */
    public Optional<User> findOne(Long userId) {
        return userRepository.findById(userId);
    }
    public Optional<User> findOneByEmailIdAndPassword(String emailId, String password) {
        return userRepository.findByEmailIdAndPassword(emailId, password);
    }


}
