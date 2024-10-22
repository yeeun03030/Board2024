package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.BoardDTO;
import kr.ac.kopo.board.dto.PageRequestDTO;
import kr.ac.kopo.board.dto.PageResultDTO;
import kr.ac.kopo.board.entity.Board;
import kr.ac.kopo.board.entity.Member;
import kr.ac.kopo.board.repository.BoardRepository;
import kr.ac.kopo.board.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

@Service
@RequiredArgsConstructor // repository를 자동으로 주입하기 위해
public class BoardServiceImpl implements BoardService {
    private final BoardRepository repository;
    private final ReplyRepository replyRepository;

    @Override
    public Long register(BoardDTO dto) {
        Board board = dtoToEntity(dto);

        repository.save(board);

        return board.getBno();
    }

    @Override
    public PageResultDTO<BoardDTO, Object[]> getList(PageRequestDTO pageRequestDTO) {
        Function<Object[], BoardDTO> fn = (en ->
                entityToDto((Board)en[0], (Member)en[1], (Long)en[2])); // <처리될 매개변수의 전달될 값, 반환될 자료형>

//        Page<Object[]> result = repository.getBoardWithReplyCount(
//                pageRequestDTO.getPageable(Sort.by("bno").descending())
//        );

        Page<Object[]> result = repository.searchPage(
                pageRequestDTO.getType(),
                pageRequestDTO.getKeyword(),
                pageRequestDTO.getPageable(Sort.by("bno").descending()));

        return new PageResultDTO<>(result, fn);
    }

    @Override
    public BoardDTO get(Long bno) {
        Object result = repository.getBoardByBno(bno);

        Object[] arr = (Object[]) result; // [0] = board, [1] = member, [2] = replycount
        BoardDTO boardDTO = entityToDto((Board) arr[0], (Member) arr[1], (Long) arr[2]);

        return boardDTO;
    }

    @Transactional // 두가지 이상의 처리를 하나의 작업이 된다를 것을 의미
    @Override
    public void removeWithReplies(Long bno) {
        // 댓글 삭제
        replyRepository.deleteByBno(bno);
        // 원글 삭제
        repository.deleteById(bno);
    }

    @Transactional
    @Override
    public void modify(BoardDTO boardDTO) {
        Board board = repository.getReferenceById(boardDTO.getBno()); // 지역 로딩??

        board.changeTitle(boardDTO.getTitle());
        board.changeContent(boardDTO.getContent());

        repository.save(board); // update
    }
}
