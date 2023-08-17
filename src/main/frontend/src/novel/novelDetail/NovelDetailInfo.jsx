import React, {useEffect, useState} from 'react';
import axios from "axios";
import {useLocation} from "react-router-dom";

function NovelDetailInfo(props) {
  // const [platformId, setPlatformId] = useState(props.platformId);
  const [novelDetail, setNovelDetail] = useState(props.novelDetail);
  const [novelInfo, setNovelInfo] = useState({});
  
  
  useEffect( () => {
    if (novelDetail != null) {
      setNovelInfo(novelDetail)
    }
  }, [])
  
  return (
    <div>
      {
        novelInfo.kakao
          ? <div>{novelInfo.kakao.novelTitle}</div>
          : <div>데이터가 없습니다.</div>
      }
      {
        novelInfo.naver
        ? <div>{novelInfo.naver.novelTitle}</div>
        : <div>데이터가 없습니다.</div>
      }
      {
        novelInfo.ridi
          ? <div>{novelInfo.ridi.novelTitle}</div>
          : <div>데이터가 없습니다.</div>
      }
    </div>
  )
}

export default NovelDetailInfo;