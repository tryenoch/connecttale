package com.bitc.full505_final_team4.controller;

import com.bitc.full505_final_team4.data.dto.BoardDTO;
import com.bitc.full505_final_team4.data.dto.BoardReplyDTO;
import com.bitc.full505_final_team4.data.entity.BoardCateEntity;
import com.bitc.full505_final_team4.data.entity.BoardEntity;
import com.bitc.full505_final_team4.data.entity.MemberEntity;
import com.bitc.full505_final_team4.data.entity.ReqCateEntity;
import com.bitc.full505_final_team4.service.BoardService;
import com.bitc.full505_final_team4.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @RequestMapping(value = "/req", method = RequestMethod.GET)
    // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
    public Object resList(Pageable pageable) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> reqList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getReqList(pageable);
        int totalPages = boardPages.getTotalPages();

        for (BoardEntity board : boardPages.getContent()) {
            BoardDTO req = BoardDTO.toDTO(board);
            reqList.add(req);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "req");
        result.put("boardList", reqList);
        return result;
    }

    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    public Object noticeList(Pageable pageable) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> notiList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getNotiList(pageable);
        int totalPages = boardPages.getTotalPages();

        for (BoardEntity board : boardPages) {
            BoardDTO noti = BoardDTO.toDTO(board);
            notiList.add(noti);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "notice");
        result.put("boardList", notiList);
        return result;
    }

    @RequestMapping(value = "/event", method = RequestMethod.GET)
    public Object eventList(Pageable pageable) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> eventList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getEventList(pageable);
        int totalPages = boardPages.getTotalPages();
        for (BoardEntity board : boardPages) {
            BoardDTO event = BoardDTO.toDTO(board);
            eventList.add(event);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "event");
        result.put("boardList", eventList);
        return result;
    }

    @RequestMapping(value = "/board/process", method = RequestMethod.POST)
    public Object writeBoard(BoardDTO board) throws Exception {
        Map<String, Object> result = new HashMap<>();

        int boardIdx = boardService.setBoard(board);

        result.put("result", "성공");
        result.put("boardIdx", boardIdx);

        return result;
    }

    @RequestMapping(value = "/board/{idx}", method = RequestMethod.GET)
    public Object getBoard(@PathVariable int idx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        BoardDTO board = boardService.getBoard(idx);
        List<BoardReplyDTO> replyList = boardService.getBoardReplyList(idx);

        result.put("result", "성공");
        result.put("board", board);
        result.put("replyList", replyList);
        return result;
    }

    @RequestMapping(value = "/board/{idx}", method = RequestMethod.DELETE)
    public Object removeBoard(@PathVariable int idx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        boardService.deleteBoard(idx);

        result.put("result", "성공");
        return result;
    }

    @RequestMapping(value = "/board/reply", method = RequestMethod.POST)
    public Object setBoardReply(BoardReplyDTO boardReply) throws Exception {
        Map<String, Object> result = new HashMap<>();
        boardService.setBoardReply(boardReply);

        result.put("result", "성공");
        return result;
    }

    @RequestMapping(value = "/board/reply/{idx}", method = RequestMethod.DELETE)
    public Object removeReply(@PathVariable int idx) throws Exception {
        Map<String, Object> result = new HashMap<>();
        boardService.deleteBoardReply(idx);

        result.put("result", "성공");
        return result;
    }

    @RequestMapping(value = "/board/{cate}/{keyword}", method = RequestMethod.GET)
    public Object searchReqBoard(@PathVariable int cate, @PathVariable String keyword) throws Exception {
        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> boardList = new ArrayList<>();
        List<BoardEntity> boardEntityList = null;
        switch (cate) {
            case 0:
                boardEntityList = boardService.searchTitleBoard(keyword);
                break;
            case 1:
                boardEntityList = boardService.searchWriterBoard(keyword);
                break;
        }

        for (BoardEntity boardEntity : boardEntityList) {
            BoardDTO board = BoardDTO.toDTO(boardEntity);
            boardList.add(board);
        }
        result.put("result", "성공");
        result.put("boardList", boardList);
        return result;
    }

    @RequestMapping(value = "/myPage/myQna", method = RequestMethod.GET)
    // JPA Pageable 사용(페이지네이션을 도와주는 인터페이스)
    public Object myQna(Pageable pageable, @RequestParam("id") String createId) throws Exception {

        Map<String, Object> result = new HashMap<>();

        List<BoardDTO> reqList = new ArrayList<>();
        Page<BoardEntity> boardPages = boardService.getQnaList(pageable, createId);
        int totalPages = boardPages.getTotalPages();

        for (BoardEntity board : boardPages.getContent()) {
            BoardDTO req = BoardDTO.toDTO(board);
            reqList.add(req);
        }

        result.put("result", "성공");
        result.put("totalPages", totalPages);
        result.put("nowPage", pageable.getPageNumber() + 1);
        result.put("boardType", "req");
        result.put("boardList", reqList);
        return result;
    }

    //파일 업로드 구현부
    @RequestMapping("/common/fms/ckeditor5Upload.do")
    public void fileUpload(MultipartHttpServletRequest multiRequest, HttpServletRequest request, HttpServletResponse response) {
        try {
            final String real_save_path = "C:/upload/novel/";

            // 폴더가 없을 경우 생성
            File saveFolder = new File(real_save_path);
            if(!saveFolder.exists() || saveFolder.isFile()) {
                saveFolder.mkdirs();
            }

            final Map<String, MultipartFile> files = multiRequest.getFileMap();
            MultipartFile fileload = (MultipartFile)files.get("upload");

            //filename 취득
            String fileName = fileload.getOriginalFilename();

            int index = fileName.lastIndexOf(".");
            String ext = fileName.substring(index+1);
            Random ran = new Random(System.currentTimeMillis());
            fileName = System.currentTimeMillis()+"_"+(int)(ran.nextDouble()*10000)+"."+ext;

            //폴더 경로 설정
            String newfilename = real_save_path + File.separator + fileName;
            fileload.transferTo(new File(newfilename));

            JSONObject outData = new JSONObject();
            outData.put("uploaded", true);
            outData.put("url", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/common/fms/getImageForContents.do?fileNm=" + fileName);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().print(outData.toString());
        } catch (Exception e) {
            System.out.println("오류발생");
        }
    }

    @RequestMapping("/common/fms/getImageForContents.do")
    public void getImageForContents(@RequestParam Map<String, Object> commandMap, HttpServletResponse response) throws Exception {
        String fileNm = (String)commandMap.get("fileNm");
        String fileStr = "C:/upload/novel/";

        File tmpDir = new File(fileStr);
        if(!tmpDir.exists()) {
            tmpDir.mkdirs();
        }

        FileInputStream fis = null;
        BufferedInputStream in = null;
        ByteArrayOutputStream bStream = null;

        try {

            fis = new FileInputStream(new File(fileStr, fileNm));
            in = new BufferedInputStream(fis);
            bStream = new ByteArrayOutputStream();

            int imgByte;
            while ((imgByte = in.read()) != -1) {
                bStream.write(imgByte);
            }

            String type = "";
            String ext = fileNm.substring(fileNm.lastIndexOf(".") + 1).toLowerCase();

            if ("jpg".equals(ext)) {
                type = "image/jpeg";
            } else {
                type = "image/" + ext;
            }

            response.setHeader("Content-Type", type);
            response.setContentLength(bStream.size());

            bStream.writeTo(response.getOutputStream());

            response.getOutputStream().flush();
            response.getOutputStream().close();

        } finally {
            bStream.close();
            in.close();
            fis.close();
        }

    }
}
