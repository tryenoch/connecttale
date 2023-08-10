package com.bitc.full505_final_team4.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardDTO {

    private int boardIdx;
    private String boardTitle;
    private String boardContents;
    private String createId;
    private LocalDateTime createDt;
    private String reqCate;
    private int boardCate;
}
