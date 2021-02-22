package silver.silvernote.controller.admin;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import silver.silvernote.domain.Album;
import silver.silvernote.domain.Center;
import silver.silvernote.domain.dto.SimpleResponseDto;
import silver.silvernote.domain.member.Member;
import silver.silvernote.responsemessage.HttpHeaderCreator;
import silver.silvernote.responsemessage.HttpStatusEnum;
import silver.silvernote.responsemessage.Message;
import silver.silvernote.service.AlbumService;
import silver.silvernote.service.CenterService;
import silver.silvernote.service.MemberService;

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
public class AlbumController {

    private final AlbumService albumService;
    private final CenterService centerService;
    private final MemberService memberService;

    /**
     * 조회
     * */
    @GetMapping("/albums")
    public ResponseEntity<Message> findAlbums() {
        List<AlbumResponseDto> collect = albumService.findAlbums().stream().map(AlbumResponseDto::new).collect(Collectors.toList());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", collect), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 생성
     * */
    @PostMapping("/albums/new")
    public ResponseEntity<Message> saveAlbum(@RequestBody @Valid AlbumRequestDto request) {

        Center center = centerService.findOne(request.getCenterId()).orElseThrow(NoSuchElementException::new);
        Member member = memberService.findOne(request.getMemberId()).orElseThrow(NoSuchElementException::new);

        Album album = Album.BuilderByParam()
                    .date(request.getDate())
                    .title(request.getTitle())
                    .context(request.getContext())
                    .center(center)
                    .writer(member)
                    .build();
        
        albumService.save(album);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.CREATED, "리소스가 생성되었습니다", new SimpleResponseDto(album.getId(), LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.CREATED);
    }

    /**
     * 수정
     * */
    @PutMapping("/albums/{id}")
    public ResponseEntity<Message> updateData(@PathVariable("id") Long id,
                                              @RequestBody @Valid AlbumUpdateRequestDto request) { // 향후 파라미터가 많아지면 DTO로 수정 해야함

        albumService.updateData(id, request.getTitle(), request.getContext());

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * 삭제
     * */
    @DeleteMapping("/albums/{id}")
    public ResponseEntity<Message> deleteAlbum(@PathVariable("id") Long id) {

        albumService.deleteAlbum(id);

        return new ResponseEntity<>( // MESSAGE, HEADER, STATUS
                new Message(HttpStatusEnum.OK, "성공적으로 완료되었습니다", new SimpleResponseDto(id, LocalDateTime.now())), // STATUS, MESSAGE, DATA
                HttpHeaderCreator.createHttpHeader(),
                HttpStatus.OK);
    }

    /**
     * Request DTO
     * */
    @Data
    static class AlbumUpdateRequestDto {

        @NotBlank(message = "제목을 확인하세요")
        private String title;

        @NotBlank(message = "내용를 확인하세요")
        private String context;

    }


    @Data
    static class AlbumRequestDto {

        @NotBlank(message = "제목을 확인하세요")
        private String title;

        @NotNull(message = "날짜를 확인하세요")
        private LocalDate date;

        @NotBlank(message = "내용를 확인하세요")
        private String context;

        @NotNull(message = "센터 ID를 확인하세요")
        private Long centerId;

        @NotNull(message = "멤버 ID를 확인하세요")
        private Long memberId;

    }


    /**
     * Response DTO
     * */
    @Data // JSON 요청의 응답으로 보낼 데이터 클래스
    static class AlbumResponseDto {
        private Long id;
        private String title;
        private LocalDate date;
        private String context;

        public AlbumResponseDto(Album album) {
            this.id = album.getId();
            this.title = album.getTitle();
            this.date = album.getDate();
            this.context = album.getContext();
        }
    }
}
