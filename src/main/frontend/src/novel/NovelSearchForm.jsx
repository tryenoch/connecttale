import React from 'react';
import {Link} from "react-router-dom";
import '../static/css/default.css';

function NovelSearchForm(props) {
  return (
    <div className={'novel-search-input py-1 float-end'} >
      <form action="#" className={'mx-2 px-3'}>
        <input type="text" placeholder={'검색어를 입력해주세요.'} className={''}/>
        <Link id={'search-btn'} className={''}>
          <i className="bi bi-search me-1"></i>
        </Link>
      </form>
    </div>
  )
}

export default NovelSearchForm;