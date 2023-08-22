import React, {useState} from 'react';
import {Link, useNavigate} from "react-router-dom";
import '../static/css/default.css';

function NovelSearchForm(props) {
  
  const [searchWord, setSearchWord] = useState('');
  const searchWordChange = e => setSearchWord(e.target.value);
  const navi = useNavigate();

  const activeEnter = e => {
    if (e.key === "Enter") {
      navi(`/novelSearch?keyword=${searchWord}`);
    }
  }
  
  
  return (
    <div className={'novel-search-input py-1 float-end'} >
      <div className={'mx-2 py-1 px-3 border rounded-4'}>
        <input type="text" placeholder={'검색어를 입력해주세요.'} className={''} value={searchWord} onChange={searchWordChange} onKeyDown={activeEnter}/>
        <Link to={`/novelSearch?keyword=${searchWord}`} id={'search-btn'} className={''}>
          <i className="bi bi-search me-1"></i>
        </Link>
      </div>
    </div>
  )
}

export default NovelSearchForm;