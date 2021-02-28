package silver.silvernote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silver.silvernote.domain.DailyLearning;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningCategory;
import silver.silvernote.domain.LearningSchedule;

import java.util.List;
import java.util.Optional;

public interface DailyLearningRepository extends JpaRepository<DailyLearning, Long> {

    List<DailyLearning> findAllBySchedule(LearningSchedule schedule);
}