import React, {useEffect, useState} from 'react';
import axios from "axios";

/* 외부 함수 호출 방법 */
import * as MessageUtils from "../../common/MessageUtils";


function NovelMainTest() {

  const [title, setTitle] = useState("테스트 메시지");
  let novelId = "425270925";

  const dataTest = () => {
    //alert('테스트 메세지입니다.');
    axios.get(`/novel/naverRankList`, {
      params : {
        startNum : 0,
        endNum : 12
      }
    })
      .then(res => {
        MessageUtils.infoMessage("비동기 통신에 성공했습니다.");
        console.log(res.data.result);
        console.log(res.data.naverNovelList);
      })
      .catch(err => {
        MessageUtils.errorMessage("테스트", err);
      })
  }

  /*const dataTest = () => {
    MessageUtils.errorMessage("테스트", "에러 메세지 테스트");
  }*/


  return (
      <div>
        <button className={'btn btn-purple'} onClick={ dataTest } >테스트 버튼</button>
        <div>
          테스트 결과 : {title}
        </div>
      </div>
  )
}

export default NovelMainTest;