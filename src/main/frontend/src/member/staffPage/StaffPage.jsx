import React, {useState} from 'react';
import {Link, Route, Routes} from "react-router-dom";
import ChangeInfo from "../myPage/ChangeInfo";
import StaffMain from "./StaffMain";




function StaffPage(props) {
    return (
        <div>
            <div className={'row justify-content-center mt-5'}>
                <div className={'col-sm-8 mt-5'}>
                    <Routes>
                        <Route index path={"/"} element={<StaffMain/>}/>
                        <Route path={"/changeInfo"} element={<ChangeInfo/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default StaffPage;