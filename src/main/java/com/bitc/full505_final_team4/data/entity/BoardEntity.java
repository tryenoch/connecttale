package com.bitc.full505_final_team4.data.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "board")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int boardIdx;

    @Column(nullable = false, length = 100)
    private String boardTitle;

    @Column(nullable = false, length = 200)
    private String boardContents;

    @ManyToOne
    @JoinColumn(name = "board_cate", nullable = false)
    @ToString.Exclude
    private BoardCateEntity boardCate;

    @ManyToOne
    @JoinColumn(name = "create_id", nullable = false)
    @ToString.Exclude
    private MemberEntity createId;

    @Column(nullable = false)
    private LocalDateTime createDt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "req_cate", nullable = true)
    @ToString.Exclude
    private ReqCateEntity reqCate;

}
