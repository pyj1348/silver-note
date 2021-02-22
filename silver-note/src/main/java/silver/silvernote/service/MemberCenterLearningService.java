package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.CenterLearning;
import silver.silvernote.domain.MemberCenterLearning;
import silver.silvernote.domain.member.JoinStatus;
import silver.silvernote.domain.member.Member;
import silver.silvernote.repository.MemberCenterLearningRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCenterLearningService {
    private final MemberCenterLearningRepository memberCenterLearningRepository;
    /**
     * 멤버센터학습 등록
     */
    @Transactional
    public Long save(MemberCenterLearning memberCenterLearning) {
        memberCenterLearningRepository.save(memberCenterLearning);
        return memberCenterLearning.getId();

    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateProgress(Long id, int progress) {
        MemberCenterLearning memberCenterLearning = memberCenterLearningRepository.findById(id).orElseThrow(NoSuchElementException::new);
        memberCenterLearning.updateProgress(progress);
        memberCenterLearningRepository.save(memberCenterLearning);
    }


    /**
     * 멤버센터학습 삭제
     */
    @Transactional
    public void deleteMemberCenterLearning(Long id){
        memberCenterLearningRepository.deleteById(id);
    }

    /**
     * 전체 멤버센터학습 조회
     */
    public List<MemberCenterLearning> findMemberCenterLearnings() {
        return memberCenterLearningRepository.findAll();
    }


    /**
     * 개별 멤버센터학습 조회
     */
    public Optional<MemberCenterLearning> findOne(Long memberCenterLearningId) {
        return memberCenterLearningRepository.findById(memberCenterLearningId);
    }

    public Optional<MemberCenterLearning> findOneByDate(LocalDate date) {
        return memberCenterLearningRepository.findByDate(date);
    }


}
