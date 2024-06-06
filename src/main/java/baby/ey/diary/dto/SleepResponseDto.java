package baby.ey.diary.dto;

import baby.ey.diary.entity.Sleep;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor

public class SleepResponseDto {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;

    public SleepResponseDto(Sleep entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.created = entity.getCreated();
        this.modified = entity.getModified();
    }
}
