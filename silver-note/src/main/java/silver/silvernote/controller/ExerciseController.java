package silver.silvernote.controller;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Exercise;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.ExerciseService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;


    /**
     * 조회
     * */
    @GetMapping("/exercises")
    public ResponseEntity<Message> findExercises() {

        List<ExerciseResponseDto> collect = exerciseService.findExercises().stream().map(ExerciseResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * 생성
     * */
    @PostMapping("/exercises/new")
    public ResponseEntity<Message> saveExercise(@RequestBody @Valid ExerciseRequestDto request) {
        Exercise exercise = Exercise.BuilderByParam()
                .name(request.getName())
                .briefDescription(request.getBriefDescription())
                .fullDescription(request.getFullDescription())
                .url(request.getUrl())
                .build();

        exerciseService.save(exercise);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(exercise.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/exercises/{id}/")
    public ResponseEntity<Message> updateExercise(@PathVariable("id") Long id,
                                                  @RequestBody ExerciseRequestDto request) {

        exerciseService.updateData(id, request.getName(), request.getUrl(), request.getBriefDescription(), request.getFullDescription());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/exercises/{id}")
    public ResponseEntity<Message> deleteExercise(@PathVariable("id") Long id) {

        exerciseService.deleteExercise(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class ExerciseRequestDto {
        @NotBlank (message = "이름을 확인하세요")
        private String name;
        private String description;
        private String briefDescription;
        private String fullDescription;
        private String url;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class ExerciseResponseDto {
        private Long id;
        private String name;
        private String briefDescription;
        private String fullDescription;
        private String url;

        public ExerciseResponseDto(Exercise exercise) {
            this.id = exercise.getId();
            this.name = exercise.getName();
            this.briefDescription = exercise.getBriefDescription();
            this.fullDescription = exercise.getFullDescription();
            this.url = exercise.getUrl();
        }
    }

}
