import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import MyContent from "./MyContent";
import MyQNA from "./MyQNA";
import MyComment from "./MyComment";
import {fetchData} from "../../common/NovelDetailFetch2";

function LikeList(props) {
    const navi = useNavigate();

    const like = props.like; // 개별 소설 정보 객체
    const [novelLikeList, setNovelLikeList] = useState([]);
    const [novelLikeCount, setNovelLikeCount] = useState(0);

    /* 받아온 값 목록, dto 일부만 쓸 거기 때문에 구조 분해 할당이 안됨 */
    const rankNum = like.novelIndex;
    const title = like.novelTitle;
    const thumbnail = like.novelThumbnail;
    const adult = like.novelAdult;
    const adultsOnly = {
        Y: true,
        N: false
    };

    const handleLinkClick = async (like) => {
        console.log(like);
        try {
            const novelDetail = await fetchData(like.novelThumbnail, like.novelTitle, like.ebookCheck, like.novelIdx, like.novelAdult);
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
        <div>
            <div className={"rank-item-img"}>
                {/*세션 영역에 저장된 성인 여부에 따라 이미지 보이는 거 다르게 해야함 */}
                <Link
                    onClick={e => {
                        e.preventDefault();
                        handleLinkClick(like);
                    }} to={`/novelDetail/${title}`}>
                    {
                        adultsOnly[adult] ? <img
                                src={"https://page.kakaocdn.net/pageweb/2.12.2/public/images/img_age_19_Thumbnail_43.svg"}
                                alt={"Adults content"}/>
                            : <img src={like.novelThumbnail} alt=""/>
                    }
                </Link>
                <p className={'rank-num btn align-items-end'} onClick={() => {
                    axios.put('/novelDetailLike', null, {
                        params: {
                            novelIdx: like.novelIdx,
                            novelTitle: like.novelTitle,
                            ebookCheck: like.ebookCheck,
                            id: sessionStorage.getItem('id')
                        }
                    })
                        .then((res) => {
                            console.log(res);
                            // console.log(res);
                            axios.get("/novelDetail", {
                                params: {
                                    title: like.novelTitle,
                                    ebookCheck: like.ebookCheck
                                }
                            })
                                .then(res2 => {
                                    console.log(res2);
                                    setNovelLikeList(res2.data.novelLikeList);
                                    setNovelLikeCount(res2.data.novelLikeCount);
                                })
                                .catch(err => {
                                    console.log(err.message)
                                });

                        })
                        .catch(err => {
                            console.log(err.message);
                        });
                    window.location.reload();
                }}>
                    <i className="bi bi-heart-fill text-purple"></i>
                </p>
            </div>
            <div className={"rank-info w-100"}>
                <p className={"item-title"}>{like.novelTitle}</p>
            </div>
        </div>

    )
}

export default LikeList;