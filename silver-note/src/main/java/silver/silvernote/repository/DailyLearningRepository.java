package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.LearningSchedule;

import java.util.List;

public interface DailyLearningRepository extends JpaRepository<DailyLearning, Long> {

    List<DailyLearning> findAllBySchedule(LearningSchedule schedule);
}