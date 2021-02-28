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
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

       List<CategoryDto> parents = learningCategoryService.findMainCategories().stream().map(CategoryDto::new).collect(Collectors.toList());

       List<LearningCategoryResponseDto> categories = new ArrayList<>();

       for(CategoryDto parent : parents){
           List<CategoryDto> children = learningCategoryService.findSubCategories(parent.getId())
                   .stream().map(CategoryDto::new).collect(Collectors.toList());

           categories.add(new LearningCategoryResponseDto(parent, children));
       }

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", categories), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/learning-categories/new")
    public ResponseEntity<Message> saveLearningCategory(@RequestBody @Valid LearningCategoryRequestDto request) {
        LearningCategory category;
        if (request.getParentId() == 0){
            category = LearningCategory.BuilderByParam()
                    .name(request.getName())
                    .parent(null)
                    .build();

        }
        else{
            LearningCategory parent = learningCategoryService.findOne(request.getParentId()).orElseThrow(NoSuchElementException::new);

            category = LearningCategory.BuilderByParam()
                    .name(request.getName())
                    .parent(parent)
                    .build();
        }
        learningCategoryService.save(category);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(category.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */


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

        @NotNull
        private Long parentId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class CategoryDto {
        private Long id;
        private String name;

        public CategoryDto(LearningCategory category) {
            this.id = category.getId();
            this.name = category.getName();
        }
    }

    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class LearningCategoryResponseDto {
        private CategoryDto parent;
        private List<CategoryDto> children = new ArrayList<>();

        public LearningCategoryResponseDto(CategoryDto parent, List<CategoryDto> children) {
            this.parent = parent;
            this.children = children;
        }
    }

}
