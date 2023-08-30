package com.example.SummerProject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class User {
    @Id
    @Column
    private String id;

    @Column
    private String pw;

    @Column
    private String nickname;

    @Builder
    public User(String id, String pw, String nickname) {
        this.id = id;
        this.pw = pw;
        this.nickname = nickname;
    }

}