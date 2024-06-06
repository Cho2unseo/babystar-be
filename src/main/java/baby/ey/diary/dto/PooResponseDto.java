package baby.ey.diary.dto;

import baby.ey.diary.entity.Poo;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class PooResponseDto {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;

    public PooResponseDto(Poo entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.created = entity.getCreated();
        this.modified = entity.getModified();
    }
}
