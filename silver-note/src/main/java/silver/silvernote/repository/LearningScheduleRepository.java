package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.LearningSchedule;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LearningScheduleRepository extends JpaRepository<LearningSchedule, Long> {

    Optional<LearningSchedule> findByDate(LocalDate date);

    Optional<LearningSchedule> findByDateAndCenter(LocalDate date, Center center);

    List<LearningSchedule> findAllByCenter(Center center);
    List<LearningSchedule> findAllByCenterAndDateBetweenOrderByDate(Center center, LocalDate start, LocalDate end);


}
