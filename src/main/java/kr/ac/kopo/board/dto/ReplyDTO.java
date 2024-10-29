package kr.ac.kopo.board.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {
    private Long rno;
    private String text;
    private String replyer;
    private LocalDateTime regDate, modDate;

    private Long bno;
}
