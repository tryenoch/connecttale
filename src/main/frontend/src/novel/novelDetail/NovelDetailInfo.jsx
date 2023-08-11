import React, {useEffect, useState} from 'react';
import axios from "axios";

function NovelDetailInfo(props) {
  // const [platformId, setPlatformId] = useState(props.platformId);
  const [novelInfo, setNovelInfo] = useState({});
  
  useEffect( () => {
    const fetchData = async () => {
      try {
        const res = await axios.get("/novelDetail", {
          params: {
            platformId: props.seriesId
          }
        })
        // console.log(res);
        // DB에 저장되어있는지 유무 확인
        if (res.data != '') {
          // DB에 저장되어 있다면 novelInfo값 변경
          setNovelInfo(res);
        }
        else {
          // DB에 저장되어 있지 않다면 DB에 저장
          const res2 = await axios.get(`https://ridibooks.com/api/search-api/search?adult_exclude=n&keyword=${props.title}`)
          console.log(res2);
          
          const item2 = res2.data.books[0];
          
        }
      }
      catch (err) {
        console.log(err.message);
      }
    }
    fetchData();
    
  }, [props.seriesId])
  
  return (
    <div className={'row'}>
      <div className={'col-sm-4'}>썸네일</div>
      <div className={'col-sm-8'}>정보</div>
    </div>
  )
}

export default NovelDetailInfo;