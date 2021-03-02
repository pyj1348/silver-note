package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Exercise;
import silver.silvernote.repository.ExerciseRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class ExerciseService {
    private final ExerciseRepository exerciseRepository;
    /**
     * 운동 등록
     */
    @Transactional
    public Long save(Exercise exercise) {
        exerciseRepository.save(exercise);
        return exercise.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateData(Long id, String name, String url, String description){
        Exercise exercise = exerciseRepository.findById(id).orElseThrow(NoSuchElementException::new);
        exercise.updateData(name, url, description);
        exerciseRepository.save(exercise);
    }

    /**
     * 운동 삭제
     */

    @Transactional
    public void deleteExercise(Long id){
        exerciseRepository.deleteById(id);
    }


    /**
     * 전체 운동 조회
     */
    public List<Exercise> findExercises() {
        return exerciseRepository.findAll();
    }


    /**
     * 개별 아이템 조회
     */
    public Optional<Exercise> findOne(Long exerciseId) {
        return exerciseRepository.findById(exerciseId);
    }


}
