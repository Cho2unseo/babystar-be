package baby.ey.diary.dto;

import baby.ey.diary.entity.Meal;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MealResponseDto {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime modified;

    public MealResponseDto(Meal entity) {
        this.id = entity.getId();
        this.content = entity.getContent();
        this.created = entity.getCreated();
        this.modified = entity.getModified();
    }
}
