import React from 'react';
import {Link, Route, Routes} from "react-router-dom";
import ChangeInfo from "./ChangeInfo";
import MyMain from "./MyMain";

function MyPage(props) {

    return (
        <div>
            <div className={'row justify-content-center mt-5'}>
                <div className={'col-sm-8 mt-5'}>
                    <Routes>
                        <Route index path={"/"} element={<MyMain/>}/>
                        <Route path={"/ChangeInfo"} element={<ChangeInfo/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default MyPage;