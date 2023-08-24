import React, {useRef, useState} from 'react';
import {Swiper, SwiperSlide} from 'swiper/react';
import {Col, Row} from "react-bootstrap";
import 'swiper/css';
import 'swiper/css/pagination';
import test3 from '../../static/img/test3.png';

/* 필요한 모듈 추가하기 */
import {Autoplay, EffectFade, Navigation, Pagination} from 'swiper/modules';

function MainCarousel(props) {

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
          <SwiperSlide>
            <img src={test3} alt="test1"/>
            <div className={'slide-msg'}>
              {/* 임시 이미지 */}
              <h3>신규 플랫폼 Open!</h3>
              <h2>이야기를 잇다 <span className={'ft-yg'}>CONNECTTALE</span></h2>
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