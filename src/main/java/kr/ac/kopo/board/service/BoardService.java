package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.BoardDTO;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Member;

public interface BoardService {
    // 새로운 글을 등록하는 기능
    Long register(BoardDTO dto);

    // Repository에서 전달받은 Entity를 Dto로 변환하는 메소드
    default BoardDTO entityToDto(Board board, Member member, Long replyCount) {

        BoardDTO boardDTO = BoardDTO.builder()
                .bno(board.getBno())
                .title(board.getTitle())
                .content(board.getContent())
                .regDate(board.getRegDate())
                .modDate(board.getModDate())
                .writerEmail(member.getEmail())
                .writerName(member.getName())
                .replyCount(replyCount.intValue()) // long으로 나오므로 int로 처리하도록 변환
                .build();

        return boardDTO;
    }

    // Dto를 Entity로 변환하는 메소드
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
