import React from "react";

import axios from "axios";
import {useNavigate} from "react-router-dom";

export const fetchData = async (platformId, title, ebookCheck, ageGrade) => {
  
  // console.log(ebookCheck);
  let novelDetail = {};
  
  try {
    const res = await axios.get("/novelDetail", {
      params: {
        title: title,
        ebookCheck: ebookCheck,
        ageGrade: ageGrade
      }
    })
    
    console.log(res.data);
    
    // DB에 저장되어있는지 유무 확인
    // db에 해당 작품이 있으면 정보 꺼내오기
    if (res.data != '') {
      // DB에 저장되어 있다면 db에서 platform 데이터 들고와서 novelInfo 변경하기
      novelDetail = res.data;
      console.log(novelDetail);
      
    }
    // db에 해당 작품이 없으면 db 저장하기
    else {
      const res = await axios.post("/novelDetail", null, {
        params: {
          title: title,
          ne: ebookCheck,
          ageGrade: ageGrade
        }
      })
  
      const res2 = await axios.get("/novelDetail", {
        params: {
          title: title,
          ebookCheck: ebookCheck,
          ageGrade: ageGrade
        }
      })
      console.log(res2);
      
      if (res2.data != '') {
        // DB에 저장되어 있다면 db에서 platform 데이터 들고와서 novelInfo 변경하기
        novelDetail = res2.data;
        console.log(novelDetail);
      }
    }
  }
  catch (err) {
    console.log(err.message);
  }
  return novelDetail;
}
