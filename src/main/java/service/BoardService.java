package service;

import com.example.SummerProject.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    @Autowired // DI
    private static BoardRepository articleRepository;
}
