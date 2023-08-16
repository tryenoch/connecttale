import React from 'react';
import {Link, Route, Routes} from "react-router-dom";
import ChangeInfo from "./ChangeInfo";
import MyMain from "./MyMain";
import MyContent from "./MyContent";
import MyQNA from "./MyQNA";
import MyComment from "./MyComment";

function MyPage(props) {

    return (
        <div>
            <div className={'row justify-content-center mt-5'}>
                <div className={'col-sm-8 mt-5'}>
                    <Routes>
                        <Route index path={"/"} element={<MyMain/>}/>
                        <Route path={"/changeInfo"} element={<ChangeInfo/>}/>
                        <Route path={"/myContent"} element={<MyContent/>}/>
                        <Route path={"/myQNA"} element={<MyQNA/>}/>
                        <Route path={"/myComment"} element={<MyComment/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    )
}

export default MyPage;