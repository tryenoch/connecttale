package com.bitc.full505_final_team4.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "board_reply")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardReplyIdx;

    @Column(nullable = false, length = 300)
    private String boardReplyContents;

    @Column(nullable = false)
    private LocalDateTime createDt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "create_id", nullable = false)
    @ToString.Exclude
    private MemberEntity createId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "board_idx", nullable = false)
    @ToString.Exclude
    private BoardEntity boardIdx;
}
