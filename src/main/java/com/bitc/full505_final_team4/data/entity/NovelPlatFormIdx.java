package com.bitc.full505_final_team4.data.entity;

import lombok.*;

import java.io.Serializable;

/* novel platform table 복합키 클래스 */

//@NoArgsConstructor
//@AllArgsConstructor
//@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NovelPlatFormIdx implements Serializable {
//    @EqualsAndHashCode.Include
    private int platform;

//    @EqualsAndHashCode.Include
    private int novelIdx;
}
