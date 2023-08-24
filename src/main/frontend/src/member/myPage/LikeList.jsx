import React, {useEffect, useState} from 'react';
import {Col, Row} from "react-bootstrap";
import axios from "axios";
import {Link, useNavigate} from "react-router-dom";
import MyContent from "./MyContent";
import MyQNA from "./MyQNA";
import MyComment from "./MyComment";
import {fetchData} from "../../common/NovelDetailFetch2";
import LikeListItem from "./LikeListItem";

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


    useEffect(() => {
        setNowPage(props.defaultPage);
    }, [])

    useEffect(() => {
        requestData();
    }, [props.data, nowPage]);

    const requestData = () => {
        axios.get(`/myPage/likeList?id=${sessionStorage.getItem('id')}&page=${nowPage}&size=12`)
            .then(res => {

                console.log(res.data);

                let offset = (Math.ceil(res.data.nowPage / 5) - 1) * 5 + 1;
                let arr = [];
                let lastNum = offset + 5;
                if (lastNum > Math.ceil(res.data.totalPages / 5)) {
                    lastNum = Math.ceil(res.data.totalPages / 5) + 1;
                }
                for (let i = offset; i <= lastNum; i++) {
                    arr.push(i);
                }
                setPages(arr);
                setEndPage(res.data.totalPages);
                setAdult(res.data.likeList.novelAdult);
                setLikeList(res.data.likeList);
            })
            .catch(err => {
                alert(`통신에 실패했습니다. err : ${err}`);
            })
    }


    return (
        <div className={'container'}>
            <h1>찜한 목록</h1>
            <Row>
                {
                    likeList.length != 0
                        ? likeList.map(like => {
                            return (
                                <Col className={"rank-item"} sm key={like.novelIdx}>
                                    <LikeListItem like={like}/>
                                </Col>
                            )
                        })
                        : <div className={'d-flex justify-content-center'}>
                            <p className={'my-5'}>찜한 목록이 없습니다.</p>
                        </div>
                }
            </Row>
            <div className={'d-flex justify-content-center mx-auto my-3 pages cursor'}>
                <a
                    className={nowPage <= 0 ? 'text-black-50' : ''}
                    onClick={() => {
                        if (nowPage <= 0) {
                            return null
                        }
                        return setNowPage(0)
                    }}
                ><i className="bi bi-chevron-double-left"></i>
                </a>
                <a
                    className={nowPage <= 0 ? 'text-black-50' : ''}
                    onClick={() => {
                        if (nowPage <= 0) {
                            return null
                        }
                        return setNowPage(nowPage - 1)
                    }}>
                    <i className="bi bi-chevron-left"></i>
                </a>
                {
                    pages.map((value) => {
                        return (
                            <a
                                key={value}
                                className={nowPage === value - 1 ? 'text-black' : 'text-black-50'}
                                onClick={() => setNowPage(value - 1)}
                            >{value}</a>);
                    })
                }
                <a
                    className={nowPage >= endPage - 1 ? 'text-black-50' : ''}
                    onClick={() => {
                        if (nowPage >= endPage - 1) {
                            return null
                        }
                        return setNowPage(nowPage + 1)
                    }}
                ><i className="bi bi-chevron-right"></i>
                </a>
                <a
                    className={nowPage >= endPage - 1 ? 'text-black-50' : ''}
                    onClick={() => {
                        if (nowPage >= endPage - 1) {
                            return null
                        }
                        return setNowPage(endPage - 1)
                    }}
                ><i className="bi bi-chevron-double-right"></i>
                </a>

            </div>
        </div>
    )
}

export default LikeList;