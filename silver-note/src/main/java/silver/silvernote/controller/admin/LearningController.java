package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Learning;
import silver.silvernote.domain.LearningCategory;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.service.LearningCategoryService;
import silver.silvernote.service.LearningService;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LearningController {

    private final LearningService learningService;
    private final LearningCategoryService categoryService;

    /**
     * 조회
     * */

    @GetMapping("/learnings")
    public ResponseEntity<Message> findLearningsByCategory(@RequestParam ("categoryId") Long categoryId) {
        LearningCategory category = categoryService.findOne(categoryId).orElseThrow(NoSuchElementException::new);

        List<LearningResponseDto> collect = learningService.findLearningsByCategory(category).stream().map(LearningResponseDto::new).collect(Collectors.toList());
        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * 생성
     * */
    @PostMapping("/learnings/new")
    public ResponseEntity<Message> saveLearning(@RequestBody @Valid LearningRequestDto request) {
        LearningCategory category = categoryService.findOne(request.getCategoryId()).orElseThrow(NoSuchElementException::new);

        Learning learning = Learning.BuilderByParam()
                    .name(request.getName())
                    .description(request.getDescription())
                    .url(request.getUrl())
                    .category(category)
                    .build();

        learningService.save(learning);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(learning.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/learnings/{id}")
    public ResponseEntity<Message> updateLearning(@PathVariable("id") Long id, @RequestBody LearningRequestDto request) {

        learningService.updateData(id, request.getName(), request.getUrl(), request.getDescription());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/learnings/{id}")
    public ResponseEntity<Message> deleteLearning(@PathVariable("id") Long id) {

        learningService.deleteLearning(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class LearningRequestDto {
        @NotBlank (message = "이름을 확인하세요")
        private String name;
        private String description;
        private String url;
        private Long categoryId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class LearningResponseDto {
        private Long id;
        private String name;
        private String description;
        private String url;

        public LearningResponseDto(Learning learning) {
            this.id = learning.getId();
            this.name = learning.getName();
            this.description = learning.getDescription();
            this.url = learning.getUrl();
        }
    }

}
