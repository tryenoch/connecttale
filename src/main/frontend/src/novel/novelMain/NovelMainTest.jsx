import React, {useEffect, useState} from 'react';
import axios from "axios";

/* 외부 함수 호출 방법 */
import * as MessageUtils from "../../common/MessageUtils";


function NovelMainTest() {

  const [title, setTitle] = useState("테스트 메시지");
  let novelId = "425270925";

  const dataTest = () => {
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/testJpa`, {
      params : {
        date : getToday()
      }
    })
      .then(res => {
        MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
        console.log(res.data.rankList);
      })
      .catch(err => {
        MessageUtils.errorMessage("테스트", err);
      })
  }

  const dataTest2 = () => {
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/testJpa2`)
      .then(res => {
        MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
      })
      .catch(err => {
        MessageUtils.errorMessage("테스트", err);
      })
  }


  /*const dataTest = () => {
    MessageUtils.errorMessage("테스트", "에러 메세지 테스트");
  }*/

  // 오늘 날짜 구하는 메소드
  const getToday = () => {
    const dateInfo = new Date();

    const year = dateInfo.getFullYear();
    const month = ('0' + (dateInfo.getMonth() + 1)).slice(-2);
    const day = ('0' + dateInfo.getDate()).slice(-2);

    const today = year + '-' + month  + '-' + day;

    return today;
  }

  return (
      <div>
        <button className={'btn btn-purple'} onClick={ dataTest2 } >테스트 버튼</button>
        <div>
          테스트 결과 : {title}
        </div>
      </div>
  )
}

export default NovelMainTest;