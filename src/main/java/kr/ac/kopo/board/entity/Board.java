package kr.ac.kopo.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "writer")
public class Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 (auto-increment)
    private Long bno;

    private String title;
    private String content;

    @ManyToOne(fetch = FetchType.LAZY) // M:1 설정
    private Member writer; // Member(entity table명) writer => 실제 이름 writer_email (_email은 Member 테이블 내에 있는 email을 foreign key 설정)
}
