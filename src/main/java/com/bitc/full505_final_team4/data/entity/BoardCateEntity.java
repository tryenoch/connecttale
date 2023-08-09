package com.bitc.full505_final_team4.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board_cate")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardCateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(nullable = false, length = 45)
    private String boardName;

    @JsonIgnore
    @OneToMany(mappedBy = "boardCate", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<BoardEntity> boardList= new ArrayList<>();


}
