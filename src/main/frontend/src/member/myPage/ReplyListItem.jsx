import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import MyContent from "./MyContent";
import MyQNA from "./MyQNA";
import MyComment from "./MyComment";
import {fetchData} from "../../common/NovelDetailFetch2";

function ReplyListItem(props) {
    const navi = useNavigate();

    const reply = props.reply; // 개별 소설 정보 객체
    const [novelReplyList, setNovelreplyList] = useState([]);
    const [novelReplyCount, setNovelReplyCount] = useState(0);

    /* 받아온 값 목록, dto 일부만 쓸 거기 때문에 구조 분해 할당이 안됨 */
    const title = reply.novelIdx.novelTitle;
    const id = reply.id.id;
    const novel = reply.novelIdx;

    const handleLinkClick = async (novel) => {
        console.log(novel);
        try {
            const novelDetail = await fetchData(novel.novelThumbnail, novel.novelTitle, novel.ebookCheck, novel.novelIdx, novel.novelAdult);
            navi(`/novelDetail/${title}`, {
                state: {
                    novelDetail: novelDetail,
                }
            });
        } catch (error) {
            console.log(error.message);
        }
    };

    return (
        <tbody>
        <tr>
            <td>{reply.replyIdx}</td>
            <td className={'text-start cursor'}>
                {/*{reply != null ?*/}
                <Link
                    onClick={e => {
                        e.preventDefault();
                        handleLinkClick(novel);
                    }} to={`/novelDetail/${title}`}>
                    {title}
                </Link>
                {/*    : 'bbb'*/}
                {/*}*/}
            </td>
            <td>{reply.replyContent}</td>
            <td>
                {id}
            </td>
            <td>{reply.createDt}</td>
        </tr>
        </tbody>

    )
}

export default ReplyListItem;