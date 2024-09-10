package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.BoardDTO;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Member;
import org.springframework.stereotype.Service;

public interface BoardService {
    // 새로운 글을 등록하는 기능
    Long register(BoardDTO dto);

    default Board dtoToEntity(BoardDTO dto) {
        Member member = Member.builder() // board에서의 writer 작성을 위해 작성
                .email(dto.getWriterEmail())
                .build();

        Board board = Board.builder()
                .bno(dto.getBno())
                .title(dto.getTitle())
                .writer(member)
                .content(dto.getContent())
                .build();

        return board;
    }
}
