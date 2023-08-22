import React, {useEffect, useState} from 'react';
import axios from "axios";

import NovelDetailReport from "./NovelDetailReport";

function NovelDetailReview(props) {
  const [title, setTitle] = useState(props.novelDetail.novelIdx.novelTitle);
  const [ebookCheck, setEbookCheck] = useState(props.novelDetail.novelIdx.ebookCheck);
  const [novelAdult, setNovelAdult] = useState(props.novelDetail.novelIdx.novelAdult);
  
  // 렌더링에 사용되는 state
  const [novelInfo, setNovelInfo] = useState(props.novelDetail);
  // const [novelReplyList, setNovelReplyList] = useState(novelInfo.novelReplyList);
  
  // input에 입력값 state
  const [replyContent, setReplyContent] = useState('');
  const [spoCheck, setSpoCheck] = useState(false);
  // const [replyLikeCnt, setReplyLikeCnt] = useState(0);
  
  // 세션id값 받아오기, 세션 정보 없으면 noUser가 됨 -> 서버에서 memberEntity 못찾게끔
  const [loginId, setLoginId] = useState(sessionStorage.getItem("id")? sessionStorage.getItem("id") : 'noUser');
  
  
  useEffect(() => {
    console.log(novelInfo);
    axios.get('/novelDetail', {
      params:
        {
          title: title,
          ebookCheck: ebookCheck,
          ageGrade: novelAdult
        }
    })
      .then(res => {
        setNovelInfo(res.data)
        console.log(novelInfo);
      })

  }, [])
  
  const replyInputChange = e => setReplyContent(e.target.value);
  const spoCheckClick = e => setSpoCheck(e.target.checked)
  
  
  // 리뷰(댓글) 등록 버튼 클릭
  const replySubmit = () => {
    if (sessionStorage.getItem("id")) {
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
              title: title,
              ebookCheck: ebookCheck,
              ageGrade: novelAdult
            }
          })
            .then(res2 => {
              setNovelInfo(res2.data);
            })
        })
        .catch(err => {
          console.log(err.message);
        })
    }
    else {
      alert("회원만 이용 가능한 서비스입니다.");
    }
  }
  
  // 리뷰(댓글)에 좋아요 클릭 이벤트
  const replyLikeClick = (item, index) => {
    if (sessionStorage.getItem("id")) {
      axios.put('/novelDetailReplyLike', null, {
        params: {
          id: loginId,
          replyIdx: item.replyIdx
        }
      })
        .then(res => {
          // console.log(res);
          axios.get('/novelDetail', {
            params : {
              title: title,
              ebookCheck: ebookCheck,
              ageGrade: novelAdult
            }
          })
            .then(res2 => {
              setNovelInfo(res2.data);
            })
        })
    }
    else {
      alert("회원만 이용 가능한 서비스입니다.");
    }
  }
  
  return (
    <div>
      <p className={'fs-4 text-bold'}>#작품 리뷰</p>
      <br/>
      <p className={'text-center fs-5'}>이 작품을 평가해주세요!!</p>
      
      <div className={'mb-5 d-grid'}>
        <textarea rows={3} className={'form-control my-3'} placeholder={'스포성 댓글이나 악플은 삭제될 수 있습니다.'} value={replyContent} onChange={replyInputChange}></textarea>
        <div className={'d-flex justify-content-end'}>
          <div className={'d-flex align-items-center'}>
            <input type="checkbox" id={'spo-check'} className={'form-check-input mb-1 me-1'} checked={spoCheck} onChange={spoCheckClick}/>
            <label htmlFor="spo-check" className={'form-check-label'}>스포일러가 있습니다.</label>
            <button type={'button'} onClick={replySubmit} className={'btn btn-purple px-3 btn-sm ms-2 me-0'}>등록</button>
          </div>
        </div>

      </div>
      
      {/* 리뷰(댓글) 리스트 구간*/}
      {
        novelInfo.novelReplyList.map((item, index) => (
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
                  <button type={'button'} key={index} onClick={() => replyLikeClick(item, index)} className={'btn btn-outline-purple fs-6'} >
                    <i className="bi bi-hand-thumbs-up me-1"></i>
                    {/*좋아요 count 출력*/}
                    {
                      novelInfo.replyLikeCountList.map((item2, index2) => (
                        item.replyIdx === item2.replyIdx.replyIdx ? <span key={index2}>{item2.likeCnt}</span> : null
                      ))
                    }
                  </button>
                  {/*리뷰(댓글) 신고 부분*/}
                  <NovelDetailReport replyIdx={item.replyIdx} suspect={item.id.id} replyContent={item.replyContent}/>
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