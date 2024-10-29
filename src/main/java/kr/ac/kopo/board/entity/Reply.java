package kr.ac.kopo.board.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString(exclude = "board")
public class Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    private String text;
    private String replyer;

    // 지연 로딩 방식으로 수정한 이유: 즉시로딩을 사용할 경우 불필요한 Join을 하므로 성능저하시킬 수 있다.
    @ManyToOne(fetch = FetchType.LAZY) // 연관관계 지정
    private Board board;
}
