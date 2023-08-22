import React from 'react';
import NovelMainRank from "./novelMain/rank/NovelMainRank";
import MainCarousel from "./novelMain/MainCarousel";
import RecentNovelList from "./novelMain/recent/RecentNovelList";
import LikeNovelList from "./novelMain/like/LikeNovelList";

function NovelMain(props) {

  return (
    <div>
      <MainCarousel />
      <NovelMainRank />
      <LikeNovelList />
      <RecentNovelList />
    </div>
  )
}

export default NovelMain;