import React from 'react';

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