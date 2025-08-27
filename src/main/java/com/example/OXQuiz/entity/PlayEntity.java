package com.example.OXQuiz.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "play")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayEntity {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "user_no", nullable = false)
    private int userNo;

    @Column(name = "date_time")
    private Timestamp dateTime;

    @Column(name = "is_correct", nullable = false)
    private boolean isCorrect;

}
