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
@Table(name = "req_cate")
@Getter
@Setter
@ToString
@NoArgsConstructor
public class ReqCateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idx;

    @Column(nullable = false, length = 100)
    private String reqName;

    @JsonIgnore
    @OneToMany(mappedBy = "reqCate")
    @ToString.Exclude
    private List<BoardEntity> boardList = new ArrayList<>();

}
