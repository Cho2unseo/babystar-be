package baby.ey.crying.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Getter
public class CryingRequestsDto {
    private Long id;
    private String content;
    private LocalDateTime created;
}
