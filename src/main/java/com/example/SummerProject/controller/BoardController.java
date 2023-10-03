package com.example.SummerProject.controller;
import com.example.SummerProject.dto.BoardDto;
import com.example.SummerProject.entity.Board;
import com.example.SummerProject.repository.BoardRepository;
import com.example.SummerProject.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@Slf4j
public class BoardController {
    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 가져다가 자동 연결 시켜주는 역할
    private BoardRepository boardRepository;
    @Autowired
    private BoardService boardService;
    @GetMapping("BoardView")
    public String showBoardView()
    {
        return "/board/BoardView";
    }

    @GetMapping("BoardWritingView")
    public String showBoardWritingView()
    {
        return "/board/BoardWritingView";
    }

    @GetMapping("MainBoardView")
    public String showMainBoardView()
    {
        return "/board/MainBoardView";
    }

    //데이터 저장 로직
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
    // 등록 했을 때 각각의 게시물 보기 위한 메서드
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

    // 메인페이지 전체 게시글 리스트 가져오기 및 페이징 로직
    @GetMapping("/board")
    public String boardList(Model model, @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<Board> boardPage = boardService.boardList(pageable);
        int nowPage=boardPage.getPageable().getPageNumber();
        int startPage=Math.max(nowPage-4,1);
        int endPage=Math.min(nowPage+5,boardPage.getTotalPages());
        List<Board> boardEntityList = boardPage.getContent();
        model.addAttribute("boardList", boardEntityList);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

      //  log.info("리스트: " + boardEntityList);

        // 추가적인 페이지 정보 등록 (예: 현재 페이지 번호, 총 페이지 수 등)
        model.addAttribute("currentPage", pageable.getPageNumber()+1);
        model.addAttribute("totalPages", boardPage.getTotalPages());

        return "board/MainBoardView";
    }
    // 게시글 수정을 위한 페이지 url 매핑
    @GetMapping("/board/{id}/edit")
    public String edit(@PathVariable Long id,Model model) {
            Board boardEntity = boardRepository.findById(id).orElse(null);
            model.addAttribute("board",boardEntity);
            return "board/edit";
    }
    // 게시글 수정이 실제로 일어나는 로직
    @PostMapping("board/update")
    public String update(BoardDto dto)
    {
        Board boardEntity = dto.toEntity();
        Board target = boardRepository.findById(boardEntity.getId()).orElse(null);
        //기존에 데이터가 있다면 데이터를 갱신
        if(target!=null){
            boardRepository.save(boardEntity); // 엔티티가 디비로 갱신
        }
        //수정하고 난 후 결과페이지를 리다이렉트 시켜줌
        return "redirect:/board/"+boardEntity.getId();
    }
    // 게시글 삭제
    //@GetMapping("/board/{id}/delete")

    @GetMapping("board/{id}/delete")
    public String delete(@PathVariable Long id,Model model) {
        Board boardEntity = boardRepository.findById(id).orElse(null);
        model.addAttribute("board",boardEntity);

        return "board/delete";
    }

}