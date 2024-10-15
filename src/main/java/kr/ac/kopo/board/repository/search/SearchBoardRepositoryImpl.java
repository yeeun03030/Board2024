package kr.ac.kopo.board.repository.search;

import com.querydsl.jpa.JPQLQuery;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.QBoard;
import kr.ac.kopo.board.entity.QMember;
import kr.ac.kopo.board.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository {
    public SearchBoardRepositoryImpl() { // QuearydslRepositorySupport를 상속받을 때, 기본 생성자를 추가하지 않으면 오류가 생김
        super(Board.class);
    }

    @Override
    public Board search1() {
        log.info("search1() 메소드 호출됨");
        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        jpqlQuery.select(board, member.email, reply.count()).groupBy(board, member, reply);
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board)).groupBy(board, reply);
//        jpqlQuery.select(board).where(board.bno.eq(1L));


        log.info("========================");
        log.info(jpqlQuery);
        log.info("========================");

        // JPQL 실행방법
        List<Board> result = jpqlQuery.fetch();

        log.info("========================");
        for (Object arr : result) {
            System.out.println(arr);
        }
        log.info("========================");

//        log.info(result);
//        log.info("========================");

        return null;
    }
}
