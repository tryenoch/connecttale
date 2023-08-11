import React from 'react';

import {Route, Routes} from "react-router-dom";
import NovelTest from "./NovelTest";
import NovelMain from "./NovelMain";
import NovelMainTest from "./novelMain/NovelMainTest";
import NovelMainRank from "./novelMain/rank/NovelMainRank";

function novel(props) {

    return (
        <div>
          {/* 라우트 주소 설정은 여기서 */}
          <Routes>
            <Route index path={"/*"} element={<NovelMain />} />
            <Route path={"/novelMain"} element={<NovelMain />}/>
            <Route path={"/novelTest"} element={<NovelTest />}/>
            <Route path={"/novelMainTest"} element={<NovelMainTest />}/>
            <Route path={"/novelRank/*"} element={<NovelMainRank />} />
          </Routes>
        </div>
    )
}

export default novel;