package baby.ey.user.dto;

import baby.ey.baby.entity.Baby;
import jakarta.persistence.Column;
import lombok.Getter;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Optional;

@Getter
public class UserRequestsDto {
    private Long id;
    private String email;
    private Optional<String> password = Optional.empty();
    private Optional<String> nickname = Optional.empty();
    private Optional<Date> birthday = Optional.empty();
    private Optional<Integer> relation = Optional.empty();
    private Optional<String> babyid = Optional.empty();
    private Optional<String> path = Optional.empty();
    private LocalDateTime created;
    private LocalDateTime modified;

    public void setPath(String imagePath) {
        this.path = Optional.of(imagePath);
    }
}