import React from 'react';

export const cateList = [
  {
    id : 0,
    title : "전체"
  },
  {
    id : 1,
    title : "판타지"
  },
  {
    id : 2,
    title : "현판"
  },
  {
    id : 3,
    title : "로맨스"
  },
  {
    id : 4,
    title : "로판"
  },
  {
    id : 5,
    title : "무협"
  },
  {
    id : 6,
    title : "드라마"
  },
  {
    id : 7,
    title : "BL"
  },
  {
    id : 8,
    title : "기타"
  },
]

export function CategoryConverter(cateList){
  let cateName ="";

  if (cateList.includes("1")){
    cateName = "판타지";
  } else if(cateList.includes("2")){
    cateName = "현판";
  } else if(cateList.includes("3")){
    cateName = "로맨스";
  } else if(cateList.includes("4")){
    cateName = "로판";
  } else if(cateList.includes("5")){
    cateName = "무협";
  } else if(cateList.includes("6")){
    cateName = "드라마";
  } else if(cateList.includes("7")){
    cateName = "BL";
  } else if(cateList.includes("8")){
    cateName = "기타";
  }

  return cateName;
}