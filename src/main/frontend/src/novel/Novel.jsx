import React from 'react';

import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import NovelTest from "./NovelTest";
import NovelMain from "./NovelMain";
import NovelMainTest from "./novelMain/NovelMainTest";

function novel(props) {

    return (
        <div>
          {/* 라우트 주소 설정은 여기서 */}
          <Routes>
            <Route index element={<NovelMain />} />
            <Route path={"/novelMain"} element={<NovelMain />}/>
            <Route path={"/novelTest"} element={<NovelTest />}/>
            <Route path={"/novelTest"} element={<NovelMainTest />}/>
          </Routes>
        </div>
    )
}

export default novel;