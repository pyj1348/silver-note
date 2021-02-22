package silver.silvernote.domain.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SimpleResponseDto {
    private Long id;
    private LocalDateTime localDateTime;

    public SimpleResponseDto(Long id, LocalDateTime localDateTime) {
        this.id = id;
        this.localDateTime = localDateTime;
    }
}