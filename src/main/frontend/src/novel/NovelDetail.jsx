import React, {useEffect, useState} from 'react';
import axios from "axios";
import {getElement} from "bootstrap/js/src/util";

function NovelDetail(props) {
  const [titleList, setTitleList] = useState([]);
  
  useEffect(() => {
    axios.get('/jsoup')
      .then(res => {
        console.log(res.data);
        const titles = res.data;
        setTitleList(titles);
      })
      .catch(err => {
        alert('서버와 통신 실패');
        console.log(err.message);
      })
  }, [])
  
  return (
    <div className={'container my-4'}>
      <h1>Novel Detail</h1>
      {
        titleList.map((title, index) => {
          return (
            <li key={index}>{title}</li>
          )
        })
      }
    </div>
  )
}

export default NovelDetail;