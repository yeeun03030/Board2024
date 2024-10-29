package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

//    게시글 삭제시에 댓글도 함께 삭제
    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno") // 파라미터에서 전달받은 = ':bno'
    void deleteByBno(Long bno);

//    게시물 번호에 해당하는 댓글 목록 반환
    List<Reply> getRepliesByBoardOrderByRno(Board board);

}
