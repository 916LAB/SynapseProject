package com.example.SummerProject.repository;


import com.example.SummerProject.entity.Chatroom;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/*
    기능 : 채팅방 리파지토리
    주요 기능 : 채팅방 CRUD 실질적 작업 수행
 */
public interface ChatRoomRepository extends CrudRepository<Chatroom,String> {

    // 대화중인 모든 상대 리스트 출력을 위한 쿼리
    @Query("SELECT c.person2 FROM Chatroom c WHERE c.person1 = :person")
    List<String> findPerson(@Param("person") String person);

    // 세션에 저장된 아이디로 대화중인 상대 찾기
    @Query("SELECT c.person2 FROM Chatroom c WHERE c.person1 = :person1 and c.person2 = :person2")
    String find(@Param("person1") String person1, @Param("person2") String person2);

    @Query("SELECT c.roomid FROM Chatroom c WHERE c.person1 = :person1 and c.person2 = :person2")
    String findid(@Param("person1") String person1, @Param("person2") String person2);
}
