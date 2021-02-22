package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Album;
import silver.silvernote.domain.Notification;
import silver.silvernote.repository.AlbumRepository;
import silver.silvernote.repository.NotificationRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    /**
     * 공지 등록
     */
    @Transactional
    public Long save(Notification notification) {
        notificationRepository.save(notification);
        return notification.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateData(Long id, String title, String context){
        Notification notification = notificationRepository.findById(id).orElseThrow(NoSuchElementException::new);
        notification.updateData(title, context);
        notificationRepository.save(notification);
    }

    /**
     * 공지 삭제
     */
    @Transactional
    public void deleteNotification(Long id){
        notificationRepository.deleteById(id);
    }

    /**
     * 전체 앨범 조회
     */
    public List<Notification> findNotifications() {
        return notificationRepository.findAll();
    }

    /**
     * 개별 일정 조회
     */
    public Optional<Notification> findOne(Long albumId) {
        return notificationRepository.findById(albumId);
    }
}
