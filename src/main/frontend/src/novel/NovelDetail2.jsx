import React, {useEffect, useState} from 'react';

import {Route, Routes, useLocation, useParams, useSearchParams} from "react-router-dom";
import NovelDetail from "./NovelDetail";


function NovelDetail2(props) {
  
  
  return (
    <div>
      {/* 라우트 주소 설정은 여기서 */}
      <Routes>
        <Route index element={<NovelDetail/>} />
        <Route path={'/:title'} element={<NovelDetail />} />
      </Routes>
    </div>
  )
}

export default NovelDetail2;