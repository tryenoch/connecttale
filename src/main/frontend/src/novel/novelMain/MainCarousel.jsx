import React, {useRef, useState} from 'react';
import {Swiper, SwiperSlide} from 'swiper/react';
import {Col, Row} from "react-bootstrap";
import 'swiper/css';
import 'swiper/css/pagination';

/* 필요한 모듈 추가하기 */
import {Autoplay, EffectFade, Pagination} from 'swiper/modules';

function MainCarousel(props) {

  return (
    <div>
    <Row className={"mx-auto"}>
      <Col sm className={"d-flex justify-content-center my-4"}>

        <Swiper
          pagination={{
            /* 슬라이드 정도에 따라 캐러셀 하단 bullet 크기가 변함 */
            dynamicBullets : true,
          }}
          centeredSlides={true}
          autoplay={{
            delay: 2500,
            disableOnInteraction: false,
          }}
          loop={true}
          effect={'fade'}
          modules={[Autoplay, Pagination, EffectFade]}
          className="mySwiper"
        >
          <SwiperSlide className={""}>캐러셀 화면 1</SwiperSlide>
          <SwiperSlide>캐러셀 화면 2</SwiperSlide>
          <SwiperSlide>캐러셀 화면 3</SwiperSlide>
          <SwiperSlide>캐러셀 화면 4</SwiperSlide>
        </Swiper>

      </Col>
    </Row>
    </div>
  )
}

export default MainCarousel;