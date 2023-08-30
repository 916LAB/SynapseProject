package com.example.SummerProject.repository;


import com.example.SummerProject.entity.Chatroom;

import org.springframework.data.repository.CrudRepository;

/*
    기능 : 채팅방 리파지토리
    주요 기능 : 채팅방 CRUD 실질적 작업 수행
 */
public interface ChatRoomRepository extends CrudRepository<Chatroom,String> {


}
