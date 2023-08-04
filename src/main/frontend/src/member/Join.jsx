import React from 'react';
import {Route, Routes} from "react-router-dom";
import Join1 from "./Join1";
import Join2 from "./Join2";

function Join(props) {
  return (
    <Routes>
      <Route index element={<Join1 /> } />
      <Route path={"/join1"} element={<Join1 /> } />
      <Route path={"/join2"} element={<Join2 /> } />
    </Routes>
  )
}

export default Join;