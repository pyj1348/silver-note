package silver.silvernote.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silver.silvernote.domain.Schedule;
import silver.silvernote.repository.ScheduleRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    /**
     * 일정 등록
     */
    @Transactional
    public Long save(Schedule schedule) {
        scheduleRepository.save(schedule);
        return schedule.getId();
    }

    /**
     * 정보 변경
     */
    @Transactional
    public void updateContext(Long id, String context){
        Schedule schedule = scheduleRepository.findById(id).orElseThrow(NoSuchElementException::new);
        schedule.updateContext(context);
        scheduleRepository.save(schedule);
    }


    /**
     * 일정 삭제
     */

    @Transactional
    public void deleteSchedule(Long id){
        scheduleRepository.deleteById(id);
    }

    /**
     * 전체 일정 조회
     */
    public List<Schedule> findSchedules() {
        return scheduleRepository.findAll();
    }


    /**
     * 개별 일정 조회
     */
    public Optional<Schedule> findOne(Long scheduleId) {
        return scheduleRepository.findById(scheduleId);
    }


}
