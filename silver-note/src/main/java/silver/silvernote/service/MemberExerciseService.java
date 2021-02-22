package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.MemberCenterLearning;
import silver.silvernote.domain.MemberExercise;
import silver.silvernote.repository.MemberExerciseRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberExerciseService {
    private final MemberExerciseRepository memberExerciseRepository;
    /**
     * 멤버운동 등록
     */
    @Transactional
    public Long save(MemberExercise memberExercise) {
        memberExerciseRepository.save(memberExercise);
        return memberExercise.getId();

    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateProgress(Long id, int progress) {
        MemberExercise memberExercise = memberExerciseRepository.findById(id).orElseThrow(NoSuchElementException::new);
        memberExercise.updateProgress(progress);
        memberExerciseRepository.save(memberExercise);
    }


    /**
     * 멤버운동 삭제
     */
    @Transactional
    public void deleteMemberExercise(Long id){
        memberExerciseRepository.deleteById(id);
    }

    /**
     * 전체 멤버운동 조회
     */
    public List<MemberExercise> findMemberExercises() {
        return memberExerciseRepository.findAll();
    }


    /**
     * 개별 멤버운동 조회
     */
    public Optional<MemberExercise> findOne(Long memberExerciseId) {
        return memberExerciseRepository.findById(memberExerciseId);
    }

    public Optional<MemberExercise> findOneByDate(LocalDate date) {
        return memberExerciseRepository.findByDate(date);
    }


}
