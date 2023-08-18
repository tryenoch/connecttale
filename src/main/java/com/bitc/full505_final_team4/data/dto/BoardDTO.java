package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.BoardEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BoardDTO {

    private int boardIdx;
    private String boardTitle;
    private String boardContents;
    private String nickName;
    private String createId;
    private String createDt;
    private String reqCate;
    private int boardCate;
    private int hitCnt;

    @Builder
    public BoardDTO(int boardIdx, String boardTitle, String boardContents, String nickName, String createId, LocalDateTime createDt, String reqCate, int hitCnt) {
        this.boardIdx = boardIdx;
        this.boardTitle = boardTitle;
        this.boardContents = boardContents;
        this.nickName = nickName;
        this.createId = createId;
        // 데이터 포맷 변경
        if (createDt != null) {
            this.createDt = createDt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        }
        this.reqCate = reqCate;
        this.hitCnt = hitCnt;
    }

    public static BoardDTO toDTO(BoardEntity entity) {
        String reqName = "";
        if (entity.getReqCate() != null)
            reqName = entity.getReqCate().getReqName();
        return BoardDTO.builder()
                .boardIdx(entity.getBoardIdx())
                .boardTitle(entity.getBoardTitle())
                .boardContents(entity.getBoardContents())
                .nickName(entity.getCreateId().getNickname())
                .createId(entity.getCreateId().getId())
                .createDt(entity.getCreateDt())
                .reqCate(reqName)
                .hitCnt(entity.getHitCnt())
                .build();
    }

}
