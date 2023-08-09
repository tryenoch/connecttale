import React from 'react';
import {Route, Routes} from "react-router-dom";
import NovelRankRidi from "./NovelRankRidi";

function NovelMainRank() {
  return (
    <Routes>
      <Route index element={<NovelRankRidi />} />
      {/*<Route path={"/rankRidi"} element={<NovelRankRidi />}/>*/}
    </Routes>
  )
}

export default NovelMainRank;