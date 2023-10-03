package com.example.SummerProject.repository;

import com.example.SummerProject.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import java.util.ArrayList;

public interface BoardRepository extends JpaRepository<Board,Long> {
    @Override
    ArrayList<Board> findAll();
}