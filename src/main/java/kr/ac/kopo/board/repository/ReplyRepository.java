package kr.ac.kopo.board.repository;

import kr.ac.kopo.board.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepository extends JpaRepository<Reply, Long> {

    @Modifying
    @Query("delete from Reply r where r.board.bno = :bno") // 파라미터에서 전달받은 = ':bno'
    void deleteByBno(Long bno);
}
