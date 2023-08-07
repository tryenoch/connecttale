import React, {useState} from 'react';
import {Link} from "react-router-dom";
import '../static/css/default.css';

function NovelSearchForm(props) {
  
  const [searchWord, setSearchWord] = useState('');
  const searchWordChange = e => setSearchWord(e.target.value);
  
  return (
    <div className={'novel-search-input py-1 float-end'} >
      <form action="#" className={'mx-2 px-3'}>
        <input type="text" placeholder={'검색어를 입력해주세요.'} className={''} value={searchWord} onChange={searchWordChange}/>
        <Link to={`/novelSearch?keyword=${searchWord}`} id={'search-btn'} className={''}>
          <i className="bi bi-search me-1"></i>
        </Link>
      </form>
    </div>
  )
}

export default NovelSearchForm;