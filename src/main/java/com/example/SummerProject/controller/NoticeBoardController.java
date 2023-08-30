package com.example.SummerProject.controller;
import com.example.SummerProject.dto.BoardDto;
import com.example.SummerProject.entity.Board;
import com.example.SummerProject.repository.BoardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class NoticeBoardController {
    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결 시켜주는 역할
    private BoardRepository boardRepository;

    @GetMapping("BoardView")
    public String showBoardView()
    {
        return "board/BoardView";
    }

    @GetMapping("BoardWritingView")
    public String showBoardWritingView()
    {
        return "board/BoardWritingView";
    }

    @GetMapping("MainBoardView")
    public String showMainBoardView()
    {
        return "board/MainBoardView";
    }

    @PostMapping("/board/create")
    public String createBoard(BoardDto dto){
        log.info(dto.toString());

        // Dto를 변환  (Entity로 변환)
        Board board =dto.toEntity();
        //로깅
        log.info(board.toString());

        // db에 저장하는 역할 Repository에게 Entity를 db안에 저장하게 함
        Board saved = boardRepository.save(board);
        //로깅
        log.info(saved.toString());
        return "redirect:/board/" + saved.getId();  //redirect
    }
    @GetMapping("/board/{id}")
    public String show(@PathVariable Long id,Model model){
        log.info("id = " + id );

        // id를 데이터로 가져옴
        Board boardEntity = boardRepository.findById(id).orElse(null); // findById로 해당 값을 찾았는데 값이 없다면 null 반환
        // 가져온 데이터를 모델에 등록
        model.addAttribute("board",boardEntity);
        // 보여줄 페이지 설정

        return "board/BoardView";
    }

    // 메인페이지 전체 게시글 리스트 가져오기
    @GetMapping("/board")
    public String index(Model model) {
        //모든 board를 가져옴
        List<Board> boardEntityList = boardRepository.findAll();
        model.addAttribute("boardList",boardEntityList);

        return "board/MainBoardView";
    }
}
