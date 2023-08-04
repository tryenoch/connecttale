import React from 'react';
import {Link, Route, Routes} from "react-router-dom";
import Join2 from "./Join2";
import Join1 from "./Join1";
import Join3 from "./Join3";


function Join(props) {

    return (
        <div className={'container mt-5'}>
            <div className={'my-5'}>
                <br/>
            </div>
            <h1 className={'text-center my-5'}>회원가입</h1>
            <div className={'my-5'}>
                <br/>
            </div>
            <div className={'row justify-content-center mt-5'}>
                <Routes>
                    <Route index path={"/"} element={<Join1/>}/>
                    <Route path={"/Join2"} element={<Join2/>}/>
                    <Route path={"/Join3"} element={<Join3/>}/>
                </Routes>
            </div>
            <br/><br/><br/><br/><br/><br/><br/>
        </div>

    )
}

export default Join;