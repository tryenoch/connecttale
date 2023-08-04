import React from 'react';
import {BrowserRouter, Link, Route, Routes} from "react-router-dom";
import NovelTest from "./NovelTest";
import NovelMain from "./NovelMain";

function novel(props) {

    return (
        <div>
          <Routes>
            <Route index element={<NovelMain />} />
            <Route path={"/novelMain"} element={<NovelMain />}/>
            <Route path={"/novelTest"} element={<NovelTest />}/>
          </Routes>
        </div>
    )
}

export default novel;