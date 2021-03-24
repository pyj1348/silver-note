package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Admin;
import silver.silvernote.domain.Album;
import silver.silvernote.repository.AdminRepository;
import silver.silvernote.repository.AlbumRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {
    private final AdminRepository adminRepository;
    /**
     * 앨범 등록
     */
    @Transactional
    public Long save(Admin admin) {
        adminRepository.save(admin);
        return admin.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public Long updatePassword(Long id, String password){
        Admin admin = adminRepository.findById(id).orElseThrow(NoSuchElementException::new);
        admin.updatePassword(password);
        adminRepository.save(admin);
        return admin.getId();
    }

    /**
     * 본사 관리자 계정 삭제
     */
    @Transactional
    public void deleteAdmin(Long id){
        adminRepository.deleteById(id);
    }

    /**
     * 전체 관리자 계정 조회
     */
    public List<Admin> findAlbums() {
        return adminRepository.findAll();
    }

    /**
     * 개별 관리자 계정 조회
     */
    public Optional<Admin> findOne(Long adminId) {
        return adminRepository.findById(adminId);
    }

    /**
     * 로그인 아이디로 관리자 계정 조회
     */
    public Optional<Admin> findOneByLoginId(String loginId) {
        return adminRepository.findByLoginId(loginId);
    }

    /**
     * 로그인 비밀번호로 관리자 계정 조회
     */
    public Optional<Admin> findOneByPassword(String password) {
        return adminRepository.findByPassword(password);
    }

    /**
     * 로그인 아이디와 비밀번호로 관리자 계정 조회
     */
    public Optional<Admin> findOneByLoginIdAndPassword(String loginId, String password) {
        return adminRepository.findByLoginIdAndPassword(loginId, password);
    }
}
