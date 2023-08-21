import React, {useEffect, useState} from 'react';
import axios from "axios";
import novel from "../Novel";

function NovelDetailReview(props) {
  const [novelInfo, setNovelInfo] = useState(props.novelDetail);
  const [novelReplyList, setNovelReplyList] = useState([]);
  
  const [replyContent, setReplyContent] = useState('');
  const [spoCheck, setSpoCheck] = useState(false);
  // const [replyLikeCnt, setReplyLikeCnt] = useState(0);
  
  const [loginId, setLoginId] = useState(sessionStorage.getItem("id")? sessionStorage.getItem("id") : 'noUser');
  
  
  useEffect(() => {
    console.log(novelInfo);
    if (novelInfo.novelReplyList) {
      setNovelReplyList(novelInfo.novelReplyList);
    }

  }, [props.novelDetail])
  
  const replyInputChange = e => setReplyContent(e.target.value);
  const spoCheckClick = e => setSpoCheck(e.target.checked)
  
  
  // 리뷰(댓글) 등록 버튼 클릭
  const replySubmit = () => {
    axios.post('/novelDetailReview',null, {
      params : {
        novelIdx : novelInfo.novelIdx.novelIdx,
        id : loginId,
        replyContent : replyContent,
        spoCheck : spoCheck
      }
    })
      .then(res => {
        axios.get('/novelDetail', {
          params: {
            title: novelInfo.novelIdx.novelTitle,
            ebookCheck: novelInfo.novelIdx.ebookCheck,
            ageGrade: novelInfo.novelIdx.novelAdult
          }
        })
          .then(res2 => {
            console.log(res2);
            setNovelReplyList(res2.data.novelReplyList);
          })
      })
      .catch(err => {
        console.log(err.message);
      })
  }
  
  // const replyLikeClick = (item) => {
  //   axios.put('/novelDetailReplyLike', null, {
  //     params: {
  //       id: loginId,
  //       replyIdx: item.replyIdx
  //     }
  //   })
  //     .then(res => {
  //       // console.log(res);
  //       axios.get('/novelDetail', {
  //         params : {
  //           title: novelInfo.novelIdx.novelTitle,
  //           ebookCheck: novelInfo.novelIdx.ebookCheck,
  //           ageGrade: novelInfo.novelIdx.novelAdult
  //         }
  //       })
  //         .then(res2 => {
  //           setNovelInfo(res2.data);
  //         })
  //     })
  // }
  
  
  return (
    <div>
      <p className={'fs-4 text-bold'}>#작품 리뷰</p>
      <br/>
      <p className={'text-center fs-5'}>이 작품을 평가해주세요!!</p>
      
      <div className={'mb-5 d-grid'}>
        <textarea rows={3} className={'form-control my-3'} placeholder={'스포성 댓글이나 악플은 삭제될 수 있습니다.'} value={replyContent} onChange={replyInputChange}></textarea>
        <div className={'d-flex justify-content-end'}>
          <div className={'d-flex align-items-center'}>
            <input type="checkbox" id={'spo-check'} className={'form-check-input'} checked={spoCheck} onChange={spoCheckClick}/>
            <label htmlFor="spo-check" className={'form-check-label'}>스포일러가 있습니다.</label>
            <button type={'button'} onClick={replySubmit} className={'btn btn-purple px-3 btn-sm ms-2 me-0'}>등록</button>
          </div>
        </div>

      </div>
      
      {/* 리뷰(댓글) 리스트 구간*/}
      {
        novelReplyList.map((item, index) => (
          item.spoilerYn == 'N' ?
          (
            <div key={index} className={'row my-4 d-flex align-items-center border-bottom'}>
              <div className={'col-sm-3'}>
                <p>{item.id.nickname} ({item.id.id})</p>
                <p className={'text-muted fs-6'}>{item.createDt}</p>
              </div>
              <div className={'col-sm-6'}>
                <p>{item.replyContent}</p>
              </div>
              <div className={'col-sm-3 d-flex justify-content-end'}>
                <button type={'button'} className={'btn btn-outline-purple'}>좋아요()</button>
                <button className={'btn btn-outline-danger'}>신고</button>
              </div>
            </div>
          )
            : null
        ))
      }
    </div>
  )
}

export default NovelDetailReview;