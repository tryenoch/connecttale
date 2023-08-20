import React from 'react';
import NovelMainRank from "./novelMain/rank/NovelMainRank";
import MainCarousel from "./novelMain/MainCarousel";
import RecentNovelList from "./novelMain/recent/RecentNovelList";

function NovelMain(props) {

    return (
      <div>
        <MainCarousel />
        <NovelMainRank />
        <RecentNovelList />
      </div>
    )
}

export default NovelMain;