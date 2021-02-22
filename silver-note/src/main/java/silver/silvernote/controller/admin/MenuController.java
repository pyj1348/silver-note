package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.Menu;
import silver.silvernote.domain.Schedule;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.MenuService;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    private final CenterService centerService;

    /**
     * 조회
     * */
    @GetMapping("/menus")
    public ResponseEntity<Message> findMenus() {
        List<MenuResponseDto> collect = menuService.findMenus().stream().map(MenuResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/menus/new")
    public ResponseEntity<Message> saveMenu(@RequestBody @Valid MenuRequestDto request) {

        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);

        Menu menu = Menu.BuilderByParam()
                    .date(request.getDate())
                    .meal(request.getMeal())
                    .center(center)
                    .build();
        
        menuService.save(menu);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(menu.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/menus/{id}")
    public ResponseEntity<Message> updateMeal(@PathVariable("id") Long id,
                                              @RequestBody String meal) { // 향후 파라미터가 많아지면 DTO로 수정 해야함

        menuService.updateMeal(id, meal);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/meals/{id}")
    public ResponseEntity<Message> deleteItem(@PathVariable("id") Long id) {

        menuService.deleteMenu(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }
    
    /**
     * Request DTO
     * */
    @Data
    static class MenuRequestDto {

        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;

        @NotBlank(message = "식단을 확인하세요")
        private String meal;

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;
    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class MenuResponseDto {
        private Long id;
        private LocalDate date;
        private String meal;
        private Long centerId;

        public MenuResponseDto(Menu menu) {
            this.id = menu.getId();
            this.date = menu.getDate();
            this.meal = menu.getMeal();
            this.centerId = menu.getCenter().getId();
        }
    }
}
