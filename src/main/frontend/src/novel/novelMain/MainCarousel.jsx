import React, {useRef, useState} from 'react';
import {Swiper, SwiperSlide} from 'swiper/react';
import {Col, Row} from "react-bootstrap";
import 'swiper/css';
import 'swiper/css/pagination';
import bg1 from '../../static/img/bg1.png';
import bg2 from '../../static/img/bg2.png';

/* 필요한 모듈 추가하기 */
import {Autoplay, EffectFade, Navigation, Pagination} from 'swiper/modules';
import {useNavigate} from "react-router-dom";

function MainCarousel(props) {

  const navi = useNavigate();

  const moveToLink = (url) => {
    navi(url);
  }

  return (
    <div>
    <Row className={"mx-auto"}>
      <Col sm className={"d-flex justify-content-center my-4"}>
        <Swiper
          centeredSlides={true}
          autoplay={{
            delay: 3000,
            disableOnInteraction: false,
          }}
          pagination={{
            clickable: true,
            dynamicBullets : true,
          }}
          navigation={true}
          modules={[Autoplay, Pagination, Navigation]}
          className="mySwiper"
        >
          <SwiperSlide className={'bg1 carousel-cursor'} onClick={() => {moveToLink("/board/detail/2/121")}}>
            <img src={bg1} alt="bg1"/>
            <div className={'slide-msg'}>
              {/* 임시 이미지 */}
              <h3>신규 플랫폼 Open!</h3>
              <h2>이야기를 잇다 <span className={'ft-yg'}>CONNECTTALE</span></h2>
            </div>
          </SwiperSlide>
          <SwiperSlide className={'bg2 carousel-cursor'} onClick={() => {moveToLink('/board/detail/2/115')}}>
            <img src={bg2} alt="bg2"/>
            <div className={'slide-msg'}>
              {/* 임시 이미지 */}
              <h3>롯데가 우승하는 판타지가 있다?!</h3>
              <h2><span className={'ft-yg'}>천재타자가 강속구를 숨김</span></h2>
            </div>
          </SwiperSlide>
          {/*<SwiperSlide><img src={test1} alt="test1"/></SwiperSlide>
          <SwiperSlide><img src={test1} alt="test1"/></SwiperSlide>*/}
        </Swiper>

      </Col>
    </Row>
    </div>
  )
}

export default MainCarousel;