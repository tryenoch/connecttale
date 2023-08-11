package com.bitc.full505_final_team4.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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

    @JsonIgnore
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

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "req_cate", nullable = true)
    @ToString.Exclude
    private ReqCateEntity reqCate;

    // 조회수 추가를 위한 hitCnt column 추가
    @Column(name = "hit_cnt", nullable = false)
    private int hitCnt = 0;

    // save를 위한 entity builder 추가
    @Builder
    public BoardEntity(String boardTitle, String boardContents, BoardCateEntity boardCate, MemberEntity createId, ReqCateEntity reqCate) {
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.boardCate = boardCate;
        this.createId = createId;
        this.reqCate = reqCate;
    }

}
