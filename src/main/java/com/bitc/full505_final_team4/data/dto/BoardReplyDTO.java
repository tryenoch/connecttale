package com.bitc.full505_final_team4.data.dto;

import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.BoardReplyEntity;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
public class BoardReplyDTO {
    private int idx;
    private int boardIdx;
    private String reply;
    private String createId;
    private String createDt;

    @Builder
    public BoardReplyDTO(int idx, int boardIdx, String reply, String createId, LocalDateTime createDt) {
        this.idx = idx;
        this.boardIdx = boardIdx;
        this.reply = reply;
        this.createId = createId;
        // 데이터 포맷 변경
        this.createDt = createDt.format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
    }

    public static BoardReplyDTO toDTO(BoardReplyEntity entity) {
        return BoardReplyDTO.builder()
                .idx(entity.getBoardReplyIdx())
                .boardIdx(entity.getBoardIdx().getBoardIdx())
                .reply(entity.getBoardReplyContents())
                .createId(entity.getCreateId().getNickname())
                .createDt(entity.getCreateDt())
                .build();
    }
}
