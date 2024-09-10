package kr.ac.kopo.board.service;

import kr.ac.kopo.board.dto.BoardDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTests {

    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister() {
        BoardDTO dto = BoardDTO.builder()
                .title("101 Board Test...")
                .content("101 Board Test Board Test Board Test")
                .writerEmail("user3@kopo.ac.kr")
                .build();

        Long bno = boardService.register(dto); // 글 등록
        System.out.println("글이 정상적으로 등록되었습니다." + bno);
    }
}
