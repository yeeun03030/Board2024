package kr.ac.kopo.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Builder // 자동으로 생성?
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity {
    @Id
    private String email;

    private String password;

    private String name;
}
