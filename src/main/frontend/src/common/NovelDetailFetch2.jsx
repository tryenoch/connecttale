import React from "react";

import axios from "axios";
import {useNavigate} from "react-router-dom";

export const fetchData = async (platformId, title, ebookCheck, ageGrade) => {
  let novelDetail = {};
  // title의 경우 url 파라미터로 서버에 전달되는데, 문자열에 [] 등의 특수문자가 있을 경우 서버에서 인식하지 못하는 오류가 발생하기 때문에 encoding 해줌
  // => 서버에서는 인코딩된 문자열을 다시 디코딩해서 문자열로 바꿔줘야 함
  // let editTitle = title.replace('[', '\[').replace(']', '\]');
  let encodeTitle = encodeURIComponent(title);
  
  try {
    const res = await axios.get("/novelDetail", {
      params: {
        title: encodeTitle,
        ebookCheck: ebookCheck,
        ageGrade: ageGrade
      }
    })
    
    // console.log(res.data);
    
    // DB에 저장되어있는지 유무 확인
    // db에 해당 작품이 있으면 정보 꺼내오기
    if (res.data != '') {
      // DB에 저장되어 있다면 db에서 platform 데이터 들고와서 novelInfo 변경하기
      novelDetail = res.data;
      // console.log(novelDetail);
      
    }
    // db에 해당 작품이 없으면 db 저장하기
    else {
      const res = await axios.post("/novelDetail", null, {
        params: {
          title: encodeTitle,
          ne: ebookCheck,
          ageGrade: ageGrade
        }
      })
      console.log(encodeTitle);
  
      const res2 = await axios.get("/novelDetail", {
        params: {
          title: encodeTitle,
          ebookCheck: ebookCheck,
          ageGrade: ageGrade
        }
      })
      // console.log(res2);
      
      if (res2.data != '') {
        // DB에 저장되어 있다면 db에서 platform 데이터 들고와서 novelInfo 변경하기
        novelDetail = res2.data;
        // console.log(novelDetail);
      }
    }
  }
  catch (err) {
    console.log(err.message);
  }
  return novelDetail;
}
