package baby.ey.user.entity;

import baby.ey.user.dto.UserRequestsDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class User extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column
    private Date birthday;

    @Column
    private Integer relation;

    @Column
    private String babyid;

    @Column
    private String path;

    @Column
    private LocalDateTime created;

    @Column
    private LocalDateTime modified;

    public User(UserRequestsDto userRequestsDto) {
        this.id = userRequestsDto.getId();
        this.email = userRequestsDto.getEmail();
        this.password = userRequestsDto.getPassword().orElse(null);
        this.nickname = userRequestsDto.getNickname().orElse(null);
        this.birthday = userRequestsDto.getBirthday().orElse(null);
        this.relation = userRequestsDto.getRelation().orElse(null);
        this.babyid = userRequestsDto.getBabyid().orElse(null);
        this.path = userRequestsDto.getPath().orElse(null);
        this.created = LocalDateTime.now();
        this.modified = LocalDateTime.now();
    }

    public void update(UserRequestsDto userRequestsDto) {
        userRequestsDto.getPassword().ifPresent(this::setPassword);
        userRequestsDto.getBirthday().ifPresent(this::setBirthday);
        userRequestsDto.getRelation().ifPresent(this::setRelation);
        userRequestsDto.getBabyid().ifPresent(this::setBabyid);
        userRequestsDto.getPath().ifPresent(this::setPath);
        this.modified = LocalDateTime.now();
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    private void setRelation(Integer relation) {
        this.relation = relation;
    }

    private void setBabyid(String babyid) {
        this.babyid = babyid;
    }
    private void setPath(String path) {
        this.path = path;
    }

}