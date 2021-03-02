package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.Album;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    List<Album> findAlbumsByDate(LocalDate date);
    Optional<Album> findAlbumByDate(LocalDate date);
}
