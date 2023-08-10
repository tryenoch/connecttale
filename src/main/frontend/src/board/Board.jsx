import React from 'react';
import {Route, Routes} from "react-router-dom";
import BoardWrite from "./BoardWrite";
import BoardMain from "./BoardMain";


function Board(props) {

    return (
        <div>
            {/*fk*/}
            <Routes>
                <Route index element={<BoardMain/>}/>
                <Route path={"/main"} element={<BoardMain/>}/>
                <Route path={"/write"} element={<BoardWrite/>}/>
            </Routes>
        </div>
    )
}

export default Board;