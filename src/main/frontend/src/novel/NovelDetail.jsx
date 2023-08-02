import React, {useEffect, useState} from 'react';
import axios from "axios";
import {getElement} from "bootstrap/js/src/util";

function NovelDetail(props) {
  const [title, setTitle] = useState();
  
  useEffect(() => {
    axios.get('https://ridibooks.com/books/4159000001?_rdt_sid=bestseller_romance_fantasy_serial&_rdt_idx=3')
      .then(res => {
      })
      .catch(err => {
        alert('서버와 통신 실패');
        console.log(err.message);
      })
  }, [])
  
  return (
    <div className={'container my-4'}>
      <h1>Novel Detail</h1>
    </div>
  )
}

export default NovelDetail;