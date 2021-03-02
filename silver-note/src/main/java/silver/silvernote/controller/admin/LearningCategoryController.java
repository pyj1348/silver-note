package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.LearningCategory;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.LearningCategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class LearningCategoryController {

    private final LearningCategoryService learningCategoryService;

    /**
     * 조회
     * */
    @GetMapping("/learning-categories")
    public ResponseEntity<Message> findLearningCategories() {

       List<LearningCategoryResponseDto> collect =
               learningCategoryService.findCategories().stream().map(LearningCategoryResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/learning-categories/new")
    public ResponseEntity<Message> saveLearningCategory(@RequestBody @Valid LearningCategoryRequestDto request) {

        LearningCategory category = LearningCategory.BuilderByParam()
                    .name(request.getName())
                    .build();

        learningCategoryService.save(category);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(category.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */

    @PutMapping("/learning-categories/{id}")
    public ResponseEntity<Message> updateCenter(@PathVariable("id") Long id,
                                                @RequestBody @Valid LearningCategoryRequestDto request) {

        learningCategoryService.updateName(id, request.getName());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * 삭제
     * */
    @DeleteMapping("/learning-categories/{id}")
    public ResponseEntity<Message> deleteLearningCategory(@PathVariable("id") Long id) {

        learningCategoryService.deleteCategory(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }


    /**
     * Request DTO
     * */
    @Data
    static class LearningCategoryRequestDto {

        @NotBlank(message = "이름을 확인하세요")
        private String name;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class LearningCategoryResponseDto {
        private Long id;
        private String name;

        public LearningCategoryResponseDto(LearningCategory category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }


}
