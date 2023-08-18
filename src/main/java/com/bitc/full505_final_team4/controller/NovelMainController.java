package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.common.JsonUtils;
import com.bitc.full505_final_team4.common.WebDriverUtil;
import com.bitc.full505_final_team4.data.dto.NovelDto;
import com.bitc.full505_final_team4.data.dto.NovelMainDto;
import com.bitc.full505_final_team4.data.entity.NovelEntity;
import com.bitc.full505_final_team4.data.entity.NovelPlatformEntity;
import com.bitc.full505_final_team4.data.repository.PlatformMainRepository;
import com.bitc.full505_final_team4.service.NovelKakaoService;
import com.bitc.full505_final_team4.service.NovelMainService;
import com.bitc.full505_final_team4.service.NovelNaverService;
import com.bitc.full505_final_team4.service.NovelRidiService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.TimeUnit;


@RequiredArgsConstructor
@RestController
@RequestMapping("/novel")
public class NovelMainController {

  private final NovelMainService novelMainService;
  private final NovelRidiService novelRidiService;
  private final NovelNaverService novelNaverService;
  private final NovelKakaoService novelKakaoService;

  // jpa 테스트용
  @GetMapping("/testJpa1")
  public Object testJpa(@RequestParam("date") String date) throws Exception{
    Map<String ,Object> result = new HashMap<>();

    LocalDate javaDate = LocalDate.now();
    String convertDate = javaDate.toString();
    String res = "";

    if (date.equals(convertDate)){
      res = "날짜가 같습니다.";
    } else {
      res = "날짜가 틀리네요";
      novelMainService.storeRidiCategoryRankList(1750, 1);
      novelMainService.storeRidiCategoryRankList(1650, 1);
      novelMainService.storeRidiCategoryRankList(6050, 1);
      novelMainService.storeRidiCategoryRankList(4150, 1);
    }

    result.put("result", res);

    return result;
  }


  /* Json 데이터 변환 및 가져오기 테스트 */
  @GetMapping("/testJson")
  public Object testJson(@RequestParam("novelId") String novelId) throws Exception {

    // 최종 결과값을 전달할 Object
    Map<String, Object> result = new HashMap<>();

    String urlStr = "https://book-api.ridibooks.com/books/";
    urlStr += novelId;
    urlStr += "/notices";

    JSONObject resultJson = JsonUtils.jsonUrlParser(urlStr);

    JSONArray noticeList = (JSONArray) resultJson.get("notices");
    JSONObject notice = (JSONObject) noticeList.get(0);
    String noticeTitle = (String) notice.get("title");

    if(resultJson != null){
      result.put("result", "success");
      result.put("notice", noticeTitle);
    } else {
      result.put("result", "fail");
    }

    return result;
  }

  /* 리디북스 순위 리스트 가져오기 */
  @GetMapping("/ridiRankList")
  public Object getRidiRankList(@RequestParam("category") String category) throws Exception {

    Map<String, Object> result = new HashMap<>();

    /* 실시간 순위 1위부터 20위까지 리스트 반환 */
    List<NovelMainDto> ridiNovelList = novelMainService.getRidiRankList(category, 1);

    if(ridiNovelList != null){
      result.put("result", "success");
      result.put("ridiNovelList", ridiNovelList);
    } else {
      result.put("result", "Backend error");
    }

    return result;
  }

  /* 네이버 순위 리스트 가져오기 */
  @GetMapping("/naverRankList")
  public Object getNaverRankList(@RequestParam("startNum") String startNum, @RequestParam("endNum") String endNum) throws Exception{
    Map<String, Object> result = new HashMap<>();

    List<NovelMainDto> naverNovelList = novelMainService.getNaverRankList(startNum, endNum, 1);

    if(!ObjectUtils.isEmpty(naverNovelList)){
      result.put("naverNovelList", naverNovelList);
      result.put("result", "success");
    } else {
      result.put("result", "Backend error");
    }

    return result;
  }

  // 카카오 순위 리스트 가져오기
  @GetMapping("/kakaoRankList")
  public Object getKakaoRankList(@RequestParam("urlId") String urlId) throws Exception {
    Map<String, Object> result = new HashMap<>();

    List<NovelMainDto> kakaoNovelList = novelMainService.getKakaoList(urlId);
    if (!ObjectUtils.isEmpty(kakaoNovelList)){
      result.put("kakaoNovelList", kakaoNovelList);
      result.put("result", "success");
    } else {
      result.put("result", "Backend error");
    }

    return result;
  }

  /**************** 최신 소설 들고오기 ****************/

  // 리디 최신 소설 업데이트
  @GetMapping("/ridiRecentNovelUpdate")
  public Object ridiRecentNovelUpdate() throws Exception{
    Map<String, Object> result = new HashMap<>();

    boolean b1 = novelRidiService.storeRidiRecentNovel(1750);
    if (b1){
      result.put("result", "success");
    } else {
      result.put("result", "fail");
    }

    return result;
  }

  // 네이버 최신 소설 업데이트
//  private final PlatformMainRepository platformMainRepository;

  @GetMapping("/naverRecentNovelUpdate")
  public Object naverRecentNovelUpdate(@RequestParam("pageNum") String pageNum) throws Exception{

    Map<String, Object> result = new HashMap<>();

    int page = Integer.parseInt(pageNum);
    boolean b1 = novelNaverService.storeNaverRecentNovel(page);
    if (b1){
      result.put("result", "success");
    } else {
      result.put("result", "fail");
    }


    return result;
  }

  // 카카오 최신 소설 업데이트
//  private final PlatformMainRepository platformMainRepository;

  @GetMapping("/kakaoRecentNovelUpdate")
  public Object kakaoRecentNovelUpdate() throws Exception{

    Map<String, Object> result = new HashMap<>();
    boolean resultB = false;

    /*List<NovelEntity> entityList = novelKakaoService.getKakaoRecentNovelList();

    for(NovelEntity entity : entityList){

      NovelDto dto = NovelDto.toDto(entity);
      System.out.println(dto);

    }
    System.out.println("총 리스트 수 : " + entityList.size());*/

    String url = "https://page.kakao.com/menu/10011/screen/84?sort_opt=latest";

    try {

      Document doc = Jsoup.connect(url).get();

      Elements recentList = doc.select("div.mb-4pxr.flex-col > div > div.flex.grow.flex-col > div > div > div").select("a");

      String  labelText = recentList.get(1).select("div").attr("aria-label");

      System.out.println(labelText);
      System.out.println("총 리스트 수 : " + recentList.size());

      resultB = true;

    } catch (Exception e){
      System.out.println("[ERROR] 테스트 중 오류가 발생했습니다 " + e.getMessage());
    }

    result.put("result", resultB);

    return result;

  }

  // 테스트
  @GetMapping("/getTest1")
  public Object getTest1() throws Exception{
    Map<String, Object> result = new HashMap<>();
// 성공
//    String url = "https://page.kakao.com/menu/10011/screen/84?sort_opt=latest";
// 실패
    String url1 = "https://page.kakao.com/content/62199555";
    String url2 = "https://page.kakao.com/content/53725396";

    /* 셀레니움 시도 */
    WebDriver driver = WebDriverUtil.getChromeDriver();
    try {
      driver.get(url1);
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

      String  element1 = driver.findElement(By.cssSelector("div.jsx-1469927737.jsx-1458499084.jsx-2778911690.w-320pxr")).getText();

      System.out.println(element1);

      driver.get(url2);
      driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

      String  element2 = driver.findElement(By.cssSelector("div.jsx-1469927737.jsx-1458499084.jsx-2778911690.w-320pxr")).getText();

      System.out.println(element2);


      System.out.println("RESULT : Selenium 크롤링 테스트가 완료되었습니다.");
    }catch (Exception e){
      System.out.println("RESULT : Selenium 크롤링 중 오류가 발생 했습니다.");
      e.printStackTrace();
    } finally {
      driver.quit();
    }


//    Document doc = Jsoup.connect(url).get();

//    Elements recentNovel = doc.select("div.mb-4pxr.flex-col > div > div.flex.grow.flex-col > div > div > div");
//    Elements recentNovel = doc.select("div.jsx-1469927737.jsx-1458499084.jsx-2778911690.w-320pxr");
//    String  dateInfo = recentNovel.select(".info").text();
//
//    dateInfo = novelNaverService.getUpdateDateInList(dateInfo);

//    if(ObjectUtils.isEmpty(recentNovel)){
//      System.out.println("해당 요소를 찾지 못했습니다.");
//    } else{
//      System.out.println(recentNovel);
//    }

    /*boolean b1 = novelRidiService.storeRidiRecentNovel(1750);
    if (b1){
      result.put("result", "success");
    } else {
      result.put("result", "fail");
    }*/

    return result;
  }

  @GetMapping("/getTest2")
  public Object getTest2() throws Exception {

    String urlString = "https://page.kakao.com/graphql";

    try {

      URL url = new URL(urlString);

      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("POST");
      connection.setRequestProperty("Content-Type", "application/json");

      Connection conn = Jsoup.connect(urlString);
      Document html = conn.post();


    }catch (Exception e){
      System.out.println("RESULT : 크롤링 시도 중 오류가 발생 했습니다.");
      e.printStackTrace();
    }

    return null;
  }

}
