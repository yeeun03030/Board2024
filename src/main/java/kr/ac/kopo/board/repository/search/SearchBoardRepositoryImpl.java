package kr.ac.kopo.board.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.QBoard;
import kr.ac.kopo.board.entity.QMember;
import kr.ac.kopo.board.entity.QReply;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.stream.Collectors;

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

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member.email, reply.count());
        tuple.groupBy(board, member, reply);
//        jpqlQuery.leftJoin(reply).on(reply.board.eq(board)).groupBy(board, reply);
//        jpqlQuery.select(board).where(board.bno.eq(1L));


        log.info("========================");
        log.info(tuple);
        log.info("========================");

        // JPQL 실행방법
        List<Tuple> result = tuple.fetch();

        log.info("========================");
        for (Object arr : result) {
            System.out.println(arr);
        }
        log.info("========================");

//        log.info(result);
//        log.info("========================");

        return null;
    }

    @Override
    public Page<Object[]> searchPage(String type, String keyword, Pageable pageable) {
        log.info("searchPage() 메소드 호출");

        QBoard board = QBoard.board;
        QReply reply = QReply.reply;
        QMember member = QMember.member;

        JPQLQuery<Board> jpqlQuery = from(board);

        jpqlQuery.leftJoin(member).on(board.writer.eq(member));
        jpqlQuery.leftJoin(reply).on(reply.board.eq(board));

        JPQLQuery<Tuple> tuple = jpqlQuery.select(board, member, reply.count());

        BooleanBuilder booleanBuilder = new BooleanBuilder(); // 조건별로 타입에 따라 or 나 and 연산을 하여 관련 식을 만들기 편함
        BooleanExpression expression = board.bno.gt(0L);

        booleanBuilder.and(expression);

        if (type != null) {
            String[] typeArr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typeArr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "w":
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                }
            }
            booleanBuilder.and(conditionBuilder);
        }
        tuple.where(booleanBuilder);

//        Sorting(Order by)
        Sort sort = pageable.getSort();

        sort.stream().forEach(order -> {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();

            PathBuilder orderByExpression = new PathBuilder(Board.class, "board");

            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
        });

        tuple.groupBy(board, member);

//        페이지 처리에 필요한 값(offset, limit) 설정
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();

        log.info("========================");
        for (Object arr : result) {
            System.out.println(arr);
        }
        log.info("========================");

        long count = tuple.fetchCount();
        log.info("실행된 행 개수 -> COUNT: " + count);

        return new PageImpl<Object[]>(result.stream().map(t -> t.toArray()).collect(Collectors.toList()), pageable, count);
    }
}
