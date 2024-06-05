package baby.ey.diary.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class Poo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 500)
    private String content;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    @Builder
    public Poo(String content) {
        this.content = content;
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    public void update(String content) {
        this.content = content;
        this.modified = LocalDateTime.now();
    }
}

