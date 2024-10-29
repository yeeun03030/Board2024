package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.ReplyDTO;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;

import java.util.List;

public interface ReplyService {

    // 댓글 등록 메소드
    Long register(ReplyDTO replyDTO);

    // 댓글 수정 메소드
    void modify(ReplyDTO replyDTO);

    // 댓글 삭제 메소드
    void remove(Long rno);

    // 댓글 목록 반환
    List<ReplyDTO> getList(Long bno);

    default Reply dtoToEntity(ReplyDTO replyDTO) {
        Board board = Board.builder()
                .bno(replyDTO.getBno())
                .build();

        Reply reply = Reply.builder()
                .rno(replyDTO.getRno())
                .text(replyDTO.getText())
                .replyer(replyDTO.getReplyer())
                .board(board)
                .build();

        return reply;
    }

    default ReplyDTO entityToDto(Reply reply) {
        ReplyDTO replyDTO = ReplyDTO.builder()
                .rno(reply.getRno())
                .text(reply.getText())
                .replyer(reply.getReplyer())
                .regDate(reply.getRegDate())
                .modDate(reply.getModDate())
                .bno(reply.getBoard().getBno())
                .build();

        return replyDTO;
    }
}
