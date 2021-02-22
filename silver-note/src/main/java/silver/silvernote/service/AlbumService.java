package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Album;
import silver.silvernote.domain.Schedule;
import silver.silvernote.repository.AlbumRepository;
import silver.silvernote.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlbumService {
    private final AlbumRepository albumRepository;
    /**
     * 앨범 등록
     */
    @Transactional
    public Long save(Album album) {
        albumRepository.save(album);
        return album.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateData(Long id, String title, String context){
        Album album = albumRepository.findById(id).orElseThrow(NoSuchElementException::new);
        album.updateData(title, context);
        albumRepository.save(album);
    }

    /**
     * 앨범 삭제
     */
    @Transactional
    public void deleteAlbum(Long id){
        albumRepository.deleteById(id);
    }

    /**
     * 전체 앨범 조회
     */
    public List<Album> findAlbums() {
        return albumRepository.findAll();
    }

    /**
     * 개별 일정 조회
     */
    public Optional<Album> findOne(Long albumId) {
        return albumRepository.findById(albumId);
    }
}
