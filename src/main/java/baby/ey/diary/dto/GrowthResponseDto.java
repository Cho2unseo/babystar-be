package baby.ey.diary.dto;

import baby.ey.diary.entity.Growth;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class GrowthResponseDto {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;

    @Builder
    public GrowthResponseDto(Growth entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.created = entity.getCreated();
        this.modified = entity.getModified();
    }
}
