package com.example.SummerProject.repository;

import com.example.SummerProject.entity.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User,String> {
    boolean existsByNickname(String nickname);
}

