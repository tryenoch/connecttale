import React from 'react';

function Test(props){
  return (
      <div>
        <button className={"btn btn-primary"} onClick={test}>GraphQl 테스트</button>
      </div>
  )

  let endpoint = "https://page.kakao.com/graphql";

  let query = `
  query contentHomeOverview($seriesId: Long!) {
    contentHomeOverview(seriesId: $seriesId) {
        id    
        seriesId    
        displayAd {      
          ...DisplayAd      
          ...DisplayAd      
          ...DisplayAd      
          __typename    
          }    
        content {
          ...SeriesFragment      
          __typename    
        }    
        displayAd {      
          ...DisplayAd      
          __typename    
        }    
        relatedKeytalk {      
          id      
          categoryUid      
          groupUid      
          groupType      
          name      
          order      
          __typename    
        }    
        lastNoticeDate    
        moreButton {
          type      
          scheme      
          title      
          __typename    
          }
          __typename  
          }
        }
        fragment DisplayAd on DisplayAd {  
        sectionUid  
        bannerUid  
        treviUid  
        momentUid
        }
        fragment SeriesFragment on Series {  
        id  
        seriesId  
        title  
        thumbnail  
        categoryUid  
        category  
        categoryType  
        subcategoryUid  
        subcategory  
        badge  
        isAllFree  
        isWaitfree  
        is3HoursWaitfree  
        ageGrade  
        state  
        onIssue  
        authors  
        pubPeriod  
        freeSlideCount  
        lastSlideAddedDate  
        waitfreeBlockCount  
        waitfreePeriodByMinute  
        bm  
        saleState  
        serviceProperty {
          ...ServicePropertyFragment
          __typename  
        }  
        operatorProperty {
          ...OperatorPropertyFragment
          __typename  
        }  
        assetProperty {
        ...AssetPropertyFragment    
        __typename  
        }
      } 
      fragment ServicePropertyFragment on ServiceProperty {
        viewCount  
        readCount  
        ratingCount  
        ratingSum  
        commentCount  
        pageContinue {    
          ...ContinueInfoFragment    
          __typename  
        }  
      todayGift {
        ...TodayGift    
        __typename  
      }  
      waitfreeTicket {
        ...WaitfreeTicketFragment
        __typename  
      }  
      isAlarmOn  
      isLikeOn  
      ticketCount  
      purchasedDate  
      lastViewInfo {    
      ...LastViewInfoFragment    
      __typename  
      }  
      purchaseInfo {
        ...PurchaseInfoFragment
        __typename  
        }
      }
      fragment ContinueInfoFragment on ContinueInfo {
        title  
        isFree  
        productId  
        lastReadProductId  
        scheme  
        continueProductType  
        hasNewSingle  
        hasUnreadSingle
      }
      fragment TodayGift on TodayGift {
        id  
        uid  
        ticketType  
        ticketKind  
        ticketCount  
        ticketExpireAt  
        ticketExpiredText  
        isReceived
        }
      fragment WaitfreeTicketFragment on WaitfreeTicket {
        chargedPeriod  
        chargedCount  
        chargedAt
      }
      fragment LastViewInfoFragment on LastViewInfo {
        isDone  
        lastViewDate  
        rate  
        spineIndex
      }
      fragment PurchaseInfoFragment on PurchaseInfo {
        purchaseType
        rentExpireDate  
        expired
      }
      fragment OperatorPropertyFragment on OperatorProperty {
        thumbnail  
        copy  
        torosImpId  
        torosFileHashKey  
        isTextViewer
      }
      fragment AssetPropertyFragment on AssetProperty {
        bannerImage  
        cardImage  
        cardTextImage  
        cleanImage  
        ipxVideo
      }
`;

  let variables = `{
    seriesId : 62199555
  }`


  function test() {
    fetch(
        endpoint,
        {
          method : 'POST',
          headers : { 'Content-Type': 'application/json' },
          body : JSON.stringify({
            'query' : query,
            'variables' : variables
          })
        }
    )
      .then(res => res.json())
      .then(json => console.log(JSON.stringify(json.data, null, 2)))
  }

}
export default Test;

