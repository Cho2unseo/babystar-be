package baby.ey.monitoring.dto;

import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class MonitoringRequestsDto {
    private Long id;
    private String content;
    private LocalDateTime created;
}
