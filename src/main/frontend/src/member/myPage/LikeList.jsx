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

    const [novelLikeList, setNovelLikeList] = useState([]);
    const [novelLikeCount, setNovelLikeCount] = useState(0);
    const [keyword, setKeyword] = useState('');
    // 카테고리 변경 시 페이지 번호가 0으로 초기화 x
    const [nowPage, setNowPage] = useState(0);
    const [endPage, setEndPage] = useState(0);
    const [pages, setPages] = useState([1]);
    const [likeList, setLikeList] = useState([{}]);
    const [adult, setAdult] = useState([""])
    const adultsOnly = {
        Y: true,
        N: false
    };

    useEffect(() => {
        setNowPage(props.defaultPage);
    }, [])

    useEffect(() => {
        requestData();
    }, [props.data, nowPage]);

    const requestData = () => {
        axios.get(`/myPage/likeList?id=${sessionStorage.getItem('id')}&page=${nowPage}&size=10`)
            .then(res => {

                console.log(res.data);

                // let offset = (Math.ceil(res.data.nowPage / 5) - 1) * 5 + 1;
                // let arr = [];
                // let lastNum = offset + 5;
                // if (lastNum > Math.ceil(res.data.totalPages / 5)) {
                //     lastNum = Math.ceil(res.data.totalPages / 5) + 1;
                // }
                // for (let i = offset; i <= lastNum; i++) {
                //     arr.push(i);
                // }
                // setPages(arr);
                // setEndPage(res.data.totalPages);
                setAdult(res.data.likeList.novelAdult);
                setLikeList(res.data.likeList);
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
            })
    }

    // const likeClickHandler = async (like) => {
    //     axios.put('/novelDetailLike', null, {
    //         params: {
    //             novelIdx: like.novelIdx,
    //             id: sessionStorage.getItem('id')
    //         }
    //     })
    //         .then((res) => {
    //             console.log(res);
    //             // console.log(res);
    //             axios.get("/novelDetail", {
    //                 params: {
    //                     title: like.novelTitle,
    //                     ebookCheck: like.ebookCheck
    //                 }
    //             })
    //                 .then(res2 => {
    //                     console.log(res2);
    //                     setNovelLikeList(res2.data.novelLikeList);
    //                     setNovelLikeCount(res2.data.novelLikeCount);
    //                 })
    //                 .catch(err => {
    //                     console.log(err.message)
    //                 })
    //
    //         })
    //         .catch(err => {
    //             console.log(err.message);
    //         })
    // }

    const handleLinkClick = async (like) => {
        console.log(like);
        try {
            const novelDetail = await fetchData(like.novelThumbnail, like.novelTitle, like.ebookCheck, like.novelIdx, like.novelAdult);
            navi(`/novelDetail/${like.novelTitle}`, {
                state: {
                    novelDetail: novelDetail,
                }
            });
        } catch (error) {
            console.log(error.message);
        }
    };
    const handleSubmit = (event) => {
        alert(`검색어 : ${keyword}`);
        event.preventDefault();
    }

    return (
        <div className={'container'}>
            <h1>찜한 목록</h1>
            <Row>
                {
                    likeList.length != 0
                        ? likeList.map((like, index) => {
                            return (
                                <Col className={"rank-item"} sm={2} key={index}>

                                    <div>
                                        <div className={"rank-item-img"}>
                                            {/*세션 영역에 저장된 성인 여부에 따라 이미지 보이는 거 다르게 해야함 */}
                                            <Link
                                                onClick={e => {
                                                    e.preventDefault();
                                                    handleLinkClick(like);
                                                }} to={`/novelDetail/${like.title}`}>
                                                {
                                                    adultsOnly[adult] ? <img
                                                            src={"https://page.kakaocdn.net/pageweb/2.12.2/public/images/img_age_19_Thumbnail_43.svg"}
                                                            alt={"Adults content"}/>
                                                        : <img src={like.novelThumbnail} alt=""/>
                                                }
                                            </Link>
                                            <button className={'rank-num btn align-items-end'} onClick={() => {
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
                                                            })

                                                    })
                                                    .catch(err => {
                                                        console.log(err.message);
                                                    })
                                            }}>
                                                <i className="bi bi-heart-fill text-purple"></i>
                                            </button>
                                        </div>
                                    </div>
                                    <div className={"rank-info w-100"}>
                                        <p className={"item-title"}>{like.novelTitle}</p>
                                    </div>
                                </Col>
                            )
                        })
                        : <div className={'d-flex justify-content-center'}>
                            <p className={'my-5'}>찜한 목록이 없습니다.</p>
                        </div>
                }
            </Row>
        </div>
    )
}

export default LikeList;