package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import silver.silvernote.domain.Address;
import silver.silvernote.domain.member.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.repository.member.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional // (readOnly = true) // 조회만 하는 서비스에선 readOnly = true 사용 권장, 성능이 좋다
// 생성자주입 때 lombok을 쓸 거면 @AllArgsConstructor | @RequiredArgsConstructor(final 요소만)를 사용
public class MemberService {
    private final MemberRepository memberRepository;
    private final ManagerRepository managerRepository;
    private final EmployeeRepository employeeRepository;
    private final PatientRepository patientRepository;
    private final FamilyRepository familyRepository;

    /**
     * 회원가입
     */
    // 쓰기를 해야하는 서비스는 readOnly = false, default이면서 우선권도 false가 높다
    @Transactional
    public Long save(Member member) {
        memberRepository.save(member);
        return member.getId();
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 정보 수정
     */
    @Transactional
    public void updateData(Long id, String phone, Address address) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        member.updateData(phone, address);
        memberRepository.save(member);
    }

    /**
     * 가입 상태 수정
     */
    @Transactional
    public void updateStatus(Long id, JoinStatus status) {
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        member.updateStatus(status);
        memberRepository.save(member);
    }

    /**
     * 매니저 권한 상태 수정
     */
    @Transactional
    public void updateManagerPermission(Long id, PermissionStatus permission) {
        Manager manager = managerRepository.findById(id).orElseThrow(NoSuchElementException::new);
        manager.updatePermission(permission);
        memberRepository.save(manager);
    }

    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

    /**
     * 센터별 전체 회원 조회
     */
    public List<Patient> findPatientsByCenterId(Long centerId) { return patientRepository.findAllByCenterId(centerId);}

    /**
     * 역할별 전체 회원 조회
     */
    public List<Manager> findManagers() {
        return managerRepository.findAll();
    }
    public List<Manager> findWaitingManagers() { return managerRepository.findManagersByPermission(PermissionStatus.WAITING); }

    public List<Employee> findEmployees() {
        return employeeRepository.findAll();
    }
    public List<Patient> findPatients() {
        return patientRepository.findAll();
    }
    public List<Family> findFamilies() { return familyRepository.findAll(); }

    /**
     * 개별 회원 조회
     */
    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
    public Optional<Member> findOne(String name, String rrn) { return memberRepository.findByNameAndRrn(name, rrn); }


    /**
     * 역할별 개별 회원 조회
     */
    public Optional<Manager> findOneManager(Long memberId) { return managerRepository.findById(memberId); }
    public Optional<Manager> findOneManager(String name, String rrn) { return managerRepository.findByNameAndRrn(name, rrn); }

    public Optional<Employee> findOneEmployee(Long memberId) { return employeeRepository.findById(memberId); }
    public Optional<Employee> findOneEmployee(String name, String rrn) { return employeeRepository.findByNameAndRrn(name, rrn); }

    public Optional<Patient> findOnePatient(Long memberId) { return patientRepository.findById(memberId); }
    public Optional<Patient> findOnePatient(String name, String rrn) { return patientRepository.findByNameAndRrn(name, rrn); }

    public Optional<Family> findOneFamily(Long memberId) { return familyRepository.findById(memberId); }
    public Optional<Family> findOneFamily(String name, String rrn) { return familyRepository.findByNameAndRrn(name, rrn); }


}
