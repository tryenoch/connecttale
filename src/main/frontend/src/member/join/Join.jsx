import React from 'react';
import {Link, Route, Routes} from "react-router-dom";
import Join2 from "./Join2";
import Join1 from "./Join1";
import Join3 from "./Join3";


function Join(props) {

    return (
        <div className={'container mt-5 pt-3 pb-5'}>
            <h1 className={'text-center my-5 join-title'}>회원가입</h1>
            <div className={'row justify-content-center mt-5'}>
                <Routes>
                    <Route index path={"/"} element={<Join1/>}/>
                    <Route path={"/Join2"} element={<Join2/>}/>
                    <Route path={"/Join3"} element={<Join3/>}/>
                </Routes>
            </div>
        </div>
    )
}

export default Join;