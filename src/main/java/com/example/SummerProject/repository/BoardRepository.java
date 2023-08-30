package com.example.SummerProject.repository;

import com.example.SummerProject.entity.Board;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface BoardRepository extends CrudRepository<Board,Long> {
    @Override
    ArrayList<Board> findAll();
}
