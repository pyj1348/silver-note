package silver.silvernote.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleResponseDto {
    private Long id;
    private LocalDateTime time;

    public SimpleResponseDto(Long id, LocalDateTime time) {
        this.id = id;
        this.time = time;
    }
}