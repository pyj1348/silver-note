package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.MemberLearning;
import silver.silvernote.repository.MemberLearningRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberLearningService {
    private final MemberLearningRepository memberLearningRepository;
    /**
     * 멤버센터학습 등록
     */
    @Transactional
    public Long save(MemberLearning memberLearning) {
        memberLearningRepository.save(memberLearning);
        return memberLearning.getId();

    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateProgress(Long id, int progress) {
        MemberLearning memberLearning = memberLearningRepository.findById(id).orElseThrow(NoSuchElementException::new);
        memberLearning.updateProgress(progress);
        memberLearningRepository.save(memberLearning);
    }


    /**
     * 멤버센터학습 삭제
     */
    @Transactional
    public void deleteMemberLearning(Long id){
        memberLearningRepository.deleteById(id);
    }

    /**
     * 전체 멤버센터학습 조회
     */
    public List<MemberLearning> findMemberLearnings() {
        return memberLearningRepository.findAll();
    }


    /**
     * 개별 멤버센터학습 조회
     */
    public Optional<MemberLearning> findOne(Long memberLearningId) {
        return memberLearningRepository.findById(memberLearningId);
    }

}
